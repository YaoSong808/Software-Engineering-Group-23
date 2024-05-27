package com.kidsbank.controller;

import com.kidsbank.entity.FileName;
import com.kidsbank.entity.GiftInfo;
import com.kidsbank.entity.TaskInfo;
import com.kidsbank.entity.UserInfo;
import com.kidsbank.util.CSVFileHandler;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class GiftPurchasedController {

    @FXML
    private TableView<GiftInfo> giftList;

    @FXML
    private TableColumn<GiftInfo, String> giftNoColumn;

    @FXML
    private TableColumn<GiftInfo, String> giftNameColumn;

    @FXML
    private TableColumn<GiftInfo, String> giftValueColumn;

    @FXML
    private TableColumn<GiftInfo, String> purchasedTimeColumn;

    @FXML
    private void initialize() {

        String userId = UserInfo.getInstance().getUserId();
        String role = UserInfo.getInstance().getRole();
        String linkId = UserInfo.getInstance().getLinkId();
        if (role.equals("parent") && linkId != null && !linkId.equals("0")){
            userId = linkId;
        }

        List<GiftInfo> listClosedGift = CSVFileHandler.getGiftList(FileName.giftsFile, userId, 4, "closed", 5);
        giftNoColumn.setCellValueFactory(new PropertyValueFactory<>("giftNo"));
        giftNameColumn.setCellValueFactory(new PropertyValueFactory<>("giftName"));
        giftValueColumn.setCellValueFactory(new PropertyValueFactory<>("giftValue"));
        purchasedTimeColumn.setCellValueFactory(new PropertyValueFactory<>("modifiedTime"));
        giftList.setItems(FXCollections.observableList(listClosedGift));

    }
}
