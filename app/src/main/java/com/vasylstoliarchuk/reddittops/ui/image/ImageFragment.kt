package com.vasylstoliarchuk.reddittops.ui.image

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Environment
import android.transition.TransitionInflater
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.vasylstoliarchuk.reddittops.R
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.image_fragment.*
import java.io.File
import javax.inject.Inject


class ImageFragment : DaggerFragment() {

    companion object {
        private const val EXTRA_ID = "id"
        private const val EXTRA_URL = "url"
        private const val EXTRA_TRANSITION_NAME = "transitionName"

        private const val REQUEST_CODE = 10101

        private val relativeLocation = Environment.DIRECTORY_PICTURES + File.separator + "reddit";
        fun newInstance(id: String, url: String?, transitionName: String?) = ImageFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_ID, id)
                putString(EXTRA_URL, url)
                putString(EXTRA_TRANSITION_NAME, transitionName)
            }
        }
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: ImageViewModel

    private lateinit var imageId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        setHasOptionsMenu(true)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move);
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.image_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imageId = arguments?.getString(EXTRA_ID) ?: ""
        val url: String? = arguments?.getString(EXTRA_URL)
        val transitionName = arguments?.getString(EXTRA_TRANSITION_NAME)

        ivImage.transitionName = transitionName
        Picasso.get()
            .load(url)
            .noFade()
            .into(ivImage, object : Callback {
                override fun onSuccess() {
                    startPostponedEnterTransition()
                }

                override fun onError(e: Exception?) {
                    startPostponedEnterTransition()
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.image_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.save) {
            when {
                ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED -> {
                    saveImage()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                    Toast.makeText(context, R.string.save_image_permission_not_granted, Toast.LENGTH_LONG).show()
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
                }
                else -> {
                    requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_CODE)
                }
            }
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun saveImage() {
        val bitmap = (ivImage.drawable as? BitmapDrawable)?.bitmap ?: return
        viewModel.saveImage(SaveImageRequest(bitmap, Bitmap.CompressFormat.JPEG, "image/jpg", imageId, "reddit"))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ImageViewModel::class.java]
        viewModel.saveImageResult.observe(viewLifecycleOwner) { success ->
            if (success) {
                Toast.makeText(context, R.string.save_image_success, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, R.string.save_image_failure, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_CODE -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    saveImage()
                } else {
                    Toast.makeText(context, R.string.save_image_permission_not_granted, Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }
}