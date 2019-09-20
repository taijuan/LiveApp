package com.live

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.alivc.live.pusher.AlivcLivePushCameraTypeEnum
import com.alivc.live.pusher.AlivcPreviewOrientationEnum
import com.live.base.BaseActivity
import com.live.base.back
import com.live.base.save
import com.live.base.title
import com.live.utils.onClick
import kotlinx.android.synthetic.main.activity_camera_style.*

//const val ORIENTATION_KEY = "orientation_key"
const val CAMERA_ID = "camera_id"
const val CAMERA_STYLE_REQUEST_CODE = 1001

class CameraStyleActivity : BaseActivity() {
    private var orientation: Int = AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
    private var cameraId = AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.ordinal
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_style)
        topBar.title(R.string.camera)
        topBar.back()

        topBar.save().onClick({
            setResult(
                Activity.RESULT_OK, Intent().putExtras(Bundle().apply {
                    putInt(ORIENTATION_KEY, orientation)
                    putInt(CAMERA_ID, cameraId)
                })
            )
            finish()
        })

        orientation = intent.getIntExtra(
            ORIENTATION_KEY,
            AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
        )
        tvHomeLeft.isSelected =
            orientation == AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT.ordinal
        tvHomeRight.isSelected =
            orientation == AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT.ordinal
        tvPortrait.isSelected =
            orientation == AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
        tvHomeLeft.onClick({
            orientation = AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_LEFT.ordinal
            tvHomeLeft.isSelected = true
            tvHomeRight.isSelected = false
            tvPortrait.isSelected = false
        })
        tvHomeRight.onClick({
            orientation = AlivcPreviewOrientationEnum.ORIENTATION_LANDSCAPE_HOME_RIGHT.ordinal
            tvHomeLeft.isSelected = false
            tvHomeRight.isSelected = true
            tvPortrait.isSelected = false
        })
        tvPortrait.onClick({
            orientation = AlivcPreviewOrientationEnum.ORIENTATION_PORTRAIT.ordinal
            tvHomeLeft.isSelected = false
            tvHomeRight.isSelected = false
            tvPortrait.isSelected = true
        })

        cameraId =
            intent.getIntExtra(CAMERA_ID, AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.ordinal)
        tvBackCamera.isSelected = cameraId == AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.ordinal
        tvFrontCamera.isSelected =
            cameraId == AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.ordinal
        tvBackCamera.onClick({
            cameraId = AlivcLivePushCameraTypeEnum.CAMERA_TYPE_BACK.ordinal
            tvBackCamera.isSelected = true
            tvFrontCamera.isSelected = false
        })
        tvFrontCamera.onClick({
            cameraId = AlivcLivePushCameraTypeEnum.CAMERA_TYPE_FRONT.ordinal
            tvFrontCamera.isSelected = true
            tvBackCamera.isSelected = false
        })
    }
}