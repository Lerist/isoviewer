/*
 * Copyright 2014 Sebastian Annies
 *
 * Licensed under the Apache License, Version 2.0 (the License);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an AS IS BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.sannies.isoviewer;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.prefs.Preferences;

public class FileOpenEventHandler implements EventHandler<ActionEvent> {
    Preferences userPrefs = Preferences.userNodeForPackage(getClass());
    Stage stage;
    IsoViewerFx isoViewerFx;

    public FileOpenEventHandler(Stage stage, IsoViewerFx isoViewerFx) {
        this.stage = stage;
        this.isoViewerFx = isoViewerFx;
    }


    public void handle(ActionEvent event) {


        FileChooser fileChooser = new FileChooser();
        String initialDirectory = userPrefs.get("fileChooserInitialDirectory", null);
        if (initialDirectory != null && new File(initialDirectory).exists() && new File(initialDirectory).isDirectory()) {
            fileChooser.setInitialDirectory(new File(initialDirectory));
        }
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP4 files", "*.mp4", "*.uvu", "*.m4v", "*.m4a", "*.uva", "*.uvv", "*.uvt", "*.mov", "*.m4s", "*.ism?"),
                new FileChooser.ExtensionFilter("All files", "*.*"));


        //Set extension filter
        //FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("AVI files (*.avi)", "*.avi");
        //fileChooser.getExtensionFilters().add(extFilter);

        //Show open file dialog
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            stage.setTitle(file.getPath());
            try {
                userPrefs.put("fileChooserInitialDirectory", file.getParentFile().getAbsolutePath());
                isoViewerFx.openFile(file);

            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException();
            }
        }
    }
}
