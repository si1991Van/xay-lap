package com.viettel.construction.server.api;

public enum APIType {
    END_URL_GET_CONSTRUCTION_MANAGEMENT,//Lay danh sach cac cong trinh o menu cong trinh
    END_DETAIL_CONSTRUCTION,//Chi tiet hang muc tu menu quan ly cong chinh
    END_LIST_CONSTRUCTION_SCHELUDE_WORK_ITEM,//Cac cong viec tu hang muc: chi tiet cua hang muc
    END_URL_LIST_PERFORM,//Lay danh sach cong viec do minh thuc hien o dashboard
    END_URL_LIST_SUPERVISE_WORK,//Lay danh sach cong viec do minh giam sat o dashboard
    END_URL_LIST_REFLECT,//Lay danh sach phan anh
    END_URL_GET_WORK_ON_DAY,//Lay cong viec trong ngay
    END_URL_GET_LIST_COMPLETE_CATEGORY,//Xac nhan hang muc hoan thanh
    END_URL_GET_LIST_SYN_STOCK_TRANS_DTO,//Load phieu xuat kho
    END_URL_GET_LIST_ENTANGLE_MANAGE,//Load vuong
    END_URL_GET_LIST_REFUND_LEVER_1,//Hoan tra vat tu thiet bi level1
    END_URL_GET_LIST_REFUND_LEVER_2,//Chi tiet cua vat tu thiet bi
    END_URL_ACCEPTANCE_LEVER_1,//Danh sach nghiem thu
    END_URL_ACCEPTANCE_LEVER_2,//Hang muc nghiem thu
    END_HANDOVER_HISTORY,//Lich su ban giao
    END_VOL_TIO,//Lich su ban giao
    END_URL_GET_CONSTRUCTION_BGMB_LIST_BY_STATUS //list Cong trinh theo trang thai BGMB

}
