/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.wm.shell.flicker.pip

import android.platform.test.annotations.Postsubmit
import android.view.Surface
import androidx.test.filters.RequiresDevice
import com.android.server.wm.flicker.FlickerParametersRunnerFactory
import com.android.server.wm.flicker.FlickerTestParameter
import com.android.server.wm.flicker.dsl.FlickerBuilder
import com.android.server.wm.flicker.startRotation
import com.android.server.wm.flicker.statusBarLayerRotatesScales
import org.junit.FixMethodOrder
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters
import org.junit.runners.Parameterized

/**
 * Test Pip launch.
 * To run this test: `atest WMShellFlickerTests:PipCloseWithSwipe`
 */
@RequiresDevice
@RunWith(Parameterized::class)
@Parameterized.UseParametersRunnerFactory(FlickerParametersRunnerFactory::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
class PipCloseWithSwipeTest(testSpec: FlickerTestParameter) : PipCloseTransition(testSpec) {
    override val transition: FlickerBuilder.(Map<String, Any?>) -> Unit
        get() = {
            super.transition(this, it)
            transitions {
                val pipRegion = wmHelper.getWindowRegion(pipApp.component).bounds
                val pipCenterX = pipRegion.centerX()
                val pipCenterY = pipRegion.centerY()
                val displayCenterX = device.displayWidth / 2
                device.swipe(pipCenterX, pipCenterY, displayCenterX, device.displayHeight, 5)
            }
        }

    @Postsubmit
    @Test
    override fun navBarLayerIsAlwaysVisible() = super.navBarLayerIsAlwaysVisible()

    @Postsubmit
    @Test
    override fun statusBarLayerIsAlwaysVisible() = super.statusBarLayerIsAlwaysVisible()

    @Postsubmit
    @Test
    override fun navBarWindowIsAlwaysVisible() = super.navBarWindowIsAlwaysVisible()

    @Postsubmit
    @Test
    override fun statusBarWindowIsAlwaysVisible() = super.statusBarWindowIsAlwaysVisible()

    @Postsubmit
    @Test
    override fun pipWindowBecomesInvisible() = super.pipWindowBecomesInvisible()

    @Postsubmit
    @Test
    override fun pipLayerBecomesInvisible() = super.pipLayerBecomesInvisible()

    @Postsubmit
    @Test
    override fun statusBarLayerRotatesScales() =
        testSpec.statusBarLayerRotatesScales(testSpec.config.startRotation, Surface.ROTATION_0)

    @Postsubmit
    @Test
    override fun noUncoveredRegions() = super.noUncoveredRegions()

    @Postsubmit
    @Test
    override fun navBarLayerRotatesAndScales() = super.navBarLayerRotatesAndScales()
}