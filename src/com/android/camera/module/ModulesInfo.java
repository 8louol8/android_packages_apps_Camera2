/*
 * Copyright (C) 2013 The Android Open Source Project
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

package com.android.camera.module;

import android.content.Context;

import com.android.camera.PhotoModule;
import com.android.camera.VideoModule;
import com.android.camera.app.AppController;
import com.android.camera.app.ModuleManager;
import com.android.camera.ui.ModeListView;
import com.android.camera.util.GcamHelper;
import com.android.camera.util.PhotoSphereHelper;
import com.android.camera.util.RefocusHelper;
import com.android.camera2.R;

/**
 * A class holding the module information and registers them to
 * {@link com.android.camera.app.ModuleManager}.
 */
public class ModulesInfo {
    private static final String TAG = "ModulesInfo";

    public static void setupModules(Context context, ModuleManager moduleManager) {
        int photoModuleId = context.getResources().getInteger(R.integer.camera_mode_photo);
        registerPhotoModule(moduleManager, photoModuleId);
        moduleManager.setDefaultModuleIndex(photoModuleId);
        registerCraftModule(moduleManager, context.getResources()
                .getInteger(R.integer.camera_mode_craft));
        registerVideoModule(moduleManager, context.getResources()
                .getInteger(R.integer.camera_mode_video));
        if (PhotoSphereHelper.hasLightCycleCapture(context)) {
            registerWideAngleModule(moduleManager, context.getResources()
                    .getInteger(R.integer.camera_mode_panorama));
            registerPhotoSphereModule(moduleManager, context.getResources()
                    .getInteger(R.integer.camera_mode_photosphere));
        }
        if (RefocusHelper.hasRefocusCapture(context)) {
            registerRefocusModule(moduleManager, context.getResources()
                    .getInteger(R.integer.camera_mode_refocus));
        }
        if (GcamHelper.hasGcamCapture()) {
            registerGcamModule(moduleManager, context.getResources()
                    .getInteger(R.integer.camera_mode_gcam));
        }
    }

    private static void registerPhotoModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public ModuleController createModule(AppController app) {
                return new PhotoModule(app);
            }
        });
    }

    private static void registerCraftModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public ModuleController createModule(AppController app) {
                return new PhotoModule(app);
            }
        });
    }

    private static void registerVideoModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public ModuleController createModule(AppController app) {
                return new VideoModule(app);
            }
        });
    }

    private static void registerWideAngleModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public ModuleController createModule(AppController app) {
                return PhotoSphereHelper.createWideAnglePanoramaModule(app);
            }
        });
    }

    private static void registerPhotoSphereModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public ModuleController createModule(AppController app) {
                return PhotoSphereHelper.createPanoramaModule(app);
            }
        });
    }

    private static void registerRefocusModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return true;
            }

            @Override
            public ModuleController createModule(AppController app) {
                // TODO: remove the type casting.
                return RefocusHelper.createRefocusModule(app);
            }
        });
    }

    private static void registerGcamModule(ModuleManager moduleManager, final int moduleId) {
        moduleManager.registerModule(new ModuleManager.ModuleAgent() {
            @Override
            public int getModuleId() {
                return moduleId;
            }

            @Override
            public boolean requestAppForCamera() {
                return false;
            }

            @Override
            public ModuleController createModule(AppController app) {
                return GcamHelper.createGcamModule(app);
            }
        });
    }
}