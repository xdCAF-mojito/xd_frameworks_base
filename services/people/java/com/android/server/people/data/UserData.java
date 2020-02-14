/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.android.server.people.data;

import android.annotation.NonNull;
import android.annotation.Nullable;
import android.annotation.UserIdInt;
import android.text.TextUtils;
import android.util.ArrayMap;

import java.util.Map;
import java.util.function.Consumer;

/** The data associated with a user profile. */
class UserData {

    private final @UserIdInt int mUserId;

    private boolean mIsUnlocked;

    private Map<String, PackageData> mPackageDataMap = new ArrayMap<>();

    UserData(@UserIdInt int userId) {
        mUserId = userId;
    }

    @UserIdInt int getUserId() {
        return mUserId;
    }

    void forAllPackages(@NonNull Consumer<PackageData> consumer) {
        for (PackageData packageData : mPackageDataMap.values()) {
            consumer.accept(packageData);
        }
    }

    void setUserUnlocked() {
        mIsUnlocked = true;
    }

    void setUserStopped() {
        mIsUnlocked = false;
    }

    boolean isUnlocked() {
        return mIsUnlocked;
    }

    /**
     * Gets the {@link PackageData} for the specified {@code packageName} if exists; otherwise
     * creates a new instance and returns it.
     */
    @NonNull
    PackageData getOrCreatePackageData(String packageName) {
        return mPackageDataMap.computeIfAbsent(
                packageName, key -> new PackageData(packageName, mUserId));
    }

    /**
     * Gets the {@link PackageData} for the specified {@code packageName} if exists; otherwise
     * returns {@code null}.
     */
    @Nullable
    PackageData getPackageData(@NonNull String packageName) {
        return mPackageDataMap.get(packageName);
    }

    void setDefaultDialer(@Nullable String packageName) {
        for (PackageData packageData : mPackageDataMap.values()) {
            if (packageData.isDefaultDialer()) {
                packageData.setIsDefaultDialer(false);
            }
            if (TextUtils.equals(packageName, packageData.getPackageName())) {
                packageData.setIsDefaultDialer(true);
            }
        }
    }

    void setDefaultSmsApp(@Nullable String packageName) {
        for (PackageData packageData : mPackageDataMap.values()) {
            if (packageData.isDefaultSmsApp()) {
                packageData.setIsDefaultSmsApp(false);
            }
            if (TextUtils.equals(packageName, packageData.getPackageName())) {
                packageData.setIsDefaultSmsApp(true);
            }
        }
    }
}
