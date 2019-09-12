/*
 * Tencent is pleased to support the open source community by making QMUI_Android available.
 *
 * Copyright (C) 2017-2018 THL A29 Limited, a Tencent company. All rights reserved.
 *
 * Licensed under the MIT License (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/MIT
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.live.widget

import android.content.Context
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.live.CreateLiveActivity
import com.live.OpenNetworkActivity
import com.live.R
import com.live.app.app
import com.live.utils.onClick
import com.live.utils.push
import com.qmuiteam.qmui.util.QMUIDisplayHelper
import com.qmuiteam.qmui.widget.popup.QMUIPopup
import kotlinx.android.synthetic.main.pop_item.view.*


class QMUMenuPopup(context: Context) : QMUIPopup(context) {

    fun create() {
        setContentView(LayoutInflater.from(mContext).inflate(R.layout.pop_item, null, false).apply {
            layoutParams = FrameLayout.LayoutParams(QMUIDisplayHelper.dp2px(app, 130), -2)
            tvNew.onClick({
                push(CreateLiveActivity::class.java)
                dismiss()
            })
            tvOpenNetwork.onClick({
                push(OpenNetworkActivity::class.java)
                dismiss()
            })
        })
    }
}
