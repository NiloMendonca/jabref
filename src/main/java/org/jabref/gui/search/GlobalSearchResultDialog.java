package org.jabref.gui.search;

import javax.inject.Inject;
import javax.swing.undo.UndoManager;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.SplitPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import org.jabref.gui.DialogService;
import org.jabref.gui.StateManager;
import org.jabref.gui.externalfiletype.ExternalFileTypes;
import org.jabref.gui.maintable.BibEntryTableViewModel;
import org.jabref.gui.maintable.MainTableColumnModel;
import org.jabref.gui.maintable.MainTableDataModel;
import org.jabref.gui.maintable.columns.FieldColumn;
import org.jabref.gui.maintable.columns.SpecialFieldColumn;
import org.jabref.gui.preview.PreviewViewer;
import org.jabref.gui.util.BaseDialog;
import org.jabref.gui.util.ValueTableCellFactory;
import org.jabref.logic.util.io.FileUtil;
import org.jabref.preferences.PreferencesService;

import com.airhacks.afterburner.views.ViewLoader;

public class GlobalSearchResultDialog extends BaseDialog<Void> {

    @FXML private SplitPane container;
    @FXML private CheckBox keepOnTop;

    private final ExternalFileTypes externalFileTypes;
    private final UndoManager undoManager;

    @Inject private PreferencesService preferencesService;
    @Inject private StateManager stateManager;
    @Inject private DialogService dialogService;

    private GlobalSearchResultDialogViewModel viewModel;

    public GlobalSearchResultDialog(ExternalFileTypes externalFileTypes, UndoManager undoManager) {
        this.undoManager = undoManager;
        this.externalFileTypes = externalFileTypes;

        ViewLoader.view(this)
                  .load()
                  .setAsDialogPane(this);
        initModality(Modality.NONE);

    }

    @FXML
    private void initialize() {
        if (preferencesService.getSearchPreferences().isKeepWindowOnTop()) {
            Stage stage = (Stage) getDialogPane().getScene().getWindow();
            stage.setAlwaysOnTop(true);
        }

        viewModel = new GlobalSearchResultDialogViewModel(stateManager, preferencesService);

        PreviewViewer previewViewer = new PreviewViewer(viewModel.getSearchDatabaseContext(), dialogService, stateManager);
        previewViewer.setTheme(preferencesService.getTheme());
        previewViewer.setLayout(preferencesService.getPreviewPreferences().getCurrentPreviewStyle());

        FieldColumn fieldColumn = new FieldColumn(MainTableColumnModel.parse("field:library"));
        new ValueTableCellFactory<BibEntryTableViewModel, String>()
                                                                   .withText(FileUtil::getBaseName)
                                                                   .install(fieldColumn);

        MainTableDataModel model = new MainTableDataModel(viewModel.getSearchDatabaseContext(), preferencesService, stateManager);
        SearchResultsTable resultsTable = new SearchResultsTable(model, viewModel.getSearchDatabaseContext(), preferencesService, undoManager, dialogService, stateManager, externalFileTypes);
        resultsTable.getColumns().add(0, fieldColumn);
        resultsTable.getColumns().removeIf(col -> col instanceof SpecialFieldColumn);
        resultsTable.getSelectionModel().selectFirst();
        resultsTable.getSelectionModel().selectedItemProperty().addListener((obs, old, newValue) -> {
            if (newValue != null) {
                previewViewer.setEntry(newValue.getEntry());
            } else {
                previewViewer.setEntry(old.getEntry());
            }
        });

        keepOnTop.selectedProperty().bindBidirectional(viewModel.keepOnTop());
        container.getItems().addAll(resultsTable, previewViewer);
    }

    public void updateSearch() {
        viewModel.updateSearch();
    }
}
