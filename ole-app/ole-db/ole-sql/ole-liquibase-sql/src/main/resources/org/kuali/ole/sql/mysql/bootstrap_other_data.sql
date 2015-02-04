--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: bootstrap_other_data.xml
--  *********************************************************************

--  Lock Database
--  Changeset bootstrap_other_data.xml::KREW_PPL_FLW_T::ole
INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10000', 'Full Approval', 'OLE-LIC', NULL, 'Y', '1', 'Full Approval People Flow for  License')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10001', 'Signatory Only', 'OLE-LIC', NULL, 'Y', '1', 'Signatory People Flow for  License')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10002', 'Review Only', 'OLE-LIC', NULL, 'Y', '1', 'Review People Flow for License')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10003', 'Approval Only', 'OLE-LIC', NULL, 'Y', '1', 'Approval People Flow for License')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10004', 'Renewal', 'OLE-LIC', NULL, 'Y', '1', 'Renewal People Flow for License')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10005', 'Addendum', 'OLE-LIC', NULL, 'Y', '1', 'Addendum People Flow for License')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10006', 'Request Approval', 'OLE', NULL, 'Y', '1', 'Request Approval People Flow')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10007', 'Acquisition Approval', 'OLE', NULL, 'Y', '1', 'Acquisition Approval for EResource')
/

INSERT INTO KREW_PPL_FLW_T (PPL_FLW_ID, NM, NMSPC_CD, TYP_ID, ACTV, VER_NBR, DESC_TXT) VALUES ('OLE10008', 'Cataloger Approval', 'OLE', NULL, 'Y', '1', 'Cataloger Approval for EResource')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('KREW_PPL_FLW_T', 'ole', 'bootstrap_other_data.xml', NOW(), 1, '7:3faf3ae7a57519400050c0a93f83c684', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset bootstrap_other_data.xml::OLE_E_RES_STAT_T::ole
INSERT INTO OLE_E_RES_STAT_T (E_RES_STAT_ID, E_RES_STAT_NM, E_RES_STAT_DESC, OBJ_ID, VER_NBR, ROW_ACT_IND) VALUES ('1', 'In-Process', 'Status of E-Res Record', 'OLEERS119', '1', 'Y')
/

INSERT INTO OLE_E_RES_STAT_T (E_RES_STAT_ID, E_RES_STAT_NM, E_RES_STAT_DESC, OBJ_ID, VER_NBR, ROW_ACT_IND) VALUES ('2', 'Trial', 'Status of E-Res Record', 'OLEERS120', '1', 'Y')
/

INSERT INTO OLE_E_RES_STAT_T (E_RES_STAT_ID, E_RES_STAT_NM, E_RES_STAT_DESC, OBJ_ID, VER_NBR, ROW_ACT_IND) VALUES ('3', 'Ordered', 'Status of E-Res Record', 'OLEERS121', '1', 'Y')
/

INSERT INTO OLE_E_RES_STAT_T (E_RES_STAT_ID, E_RES_STAT_NM, E_RES_STAT_DESC, OBJ_ID, VER_NBR, ROW_ACT_IND) VALUES ('4', 'Invoiced', 'Status of E-Res Record', 'OLEERS122', '1', 'Y')
/

INSERT INTO OLE_E_RES_STAT_T (E_RES_STAT_ID, E_RES_STAT_NM, E_RES_STAT_DESC, OBJ_ID, VER_NBR, ROW_ACT_IND) VALUES ('5', 'Active', 'Status of E-Res Record', 'OLEERS123', '1', 'Y')
/

INSERT INTO OLE_E_RES_STAT_T (E_RES_STAT_ID, E_RES_STAT_NM, E_RES_STAT_DESC, OBJ_ID, VER_NBR, ROW_ACT_IND) VALUES ('6', 'Canceled', 'Status of E-Res Record', 'OLEERS124', '1', 'Y')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_E_RES_STAT_T', 'ole', 'bootstrap_other_data.xml', NOW(), 2, '7:1cc33008043896ed508627d10964096d', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset bootstrap_other_data.xml::FS_OPTION_T::ole
INSERT INTO FS_OPTION_T (UNIV_FISCAL_YR, OBJ_ID, VER_NBR, ACT_FIN_BAL_TYP_CD, BDGT_CHK_BALTYP_CD, BDGT_CHK_OPTN_CD, UNIV_FSCYR_STRT_YR, UNIV_FSCYR_STRT_MO, FOBJTP_INC_CSH_CD, FOBJTP_XPND_EXP_CD, FOBJTP_XPNDNEXP_CD, FOBJTP_EXPNXPND_CD, FOBJ_TYP_ASSET_CD, FOBJ_TYP_LBLTY_CD, FOBJ_TYP_FNDBAL_CD, EXT_ENC_FBALTYP_CD, INT_ENC_FBALTYP_CD, PRE_ENC_FBALTYP_CD, ELIM_FINBAL_TYP_CD, FOBJTP_INC_NCSH_CD, FOBJTP_CSH_NINC_CD, UNIV_FIN_COA_CD, UNIV_FISCAL_YR_NM, FIN_BEGBALLOAD_IND, CSTSHR_ENCUM_FIN_BAL_TYP_CD, BASE_BDGT_FIN_BAL_TYP_CD, MO_BDGT_FIN_BAL_TYP_CD, FIN_OBJECT_TYP_TRNFR_INC_CD, FIN_OBJECT_TYP_TRNFR_EXP_CD, NMNL_FIN_BAL_TYP_CD) VALUES ('2012', '8d98ae-d6bc-4443-8e9b-66cca04c8aa2', '2', 'AC', 'CB', 'Y', '2011', '07', 'IN', 'EX', 'EE', 'ES', 'AS', 'LI', 'FB', 'EX', 'IE', 'PE', 'TR', 'IC', 'CH', '01', '2011-2012', 'N', 'CE', 'BB', 'MB', 'TI', 'TE', 'NB')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('FS_OPTION_T', 'ole', 'bootstrap_other_data.xml', NOW(), 3, '7:26468aa0a4a7aae7ca255480a0c98bd7', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset bootstrap_other_data.xml::FP_FSCL_YR_CTRL_T::ole
INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'BAACTV', 'b5eabbf0-409b-45a6-ac10-90e44da193c', '1', 'Y')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'BASEAD', 'b66990e6-e580-4630-96d2-9d7ca0a0d96', '1', 'Y')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'BCACTV', 'fb6b743d-dc23-4a2a-a7a8-f6c75c85bc8', '1', 'Y')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'BCGENE', 'f86a0e21-8666-48f8-a146-94935b0279d', '1', 'N')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'BCUPDT', 'de154131-455d-4abd-8160-36085ec5a05', '1', 'Y')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'BSSYNC', '2863e8e7-4af4-4a2b-9a62-d6f119084ae', '1', 'Y')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'CSFUPD', '57e0b46f-fbc6-4e4e-ae6b-469d9adf6b6', '1', 'Y')
/

INSERT INTO FP_FSCL_YR_CTRL_T (UNIV_FISCAL_YR, FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_ACTIVE_IND) VALUES ('2012', 'PSSYNC', '751a5ef8-cf76-49d8-85ed-691b6b68551', '1', 'Y')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('FP_FSCL_YR_CTRL_T', 'ole', 'bootstrap_other_data.xml', NOW(), 4, '7:1197dc0b30124babdf61989776de1aef', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset bootstrap_other_data.xml::FP_FUNC_CTRL_CD_T::ole
INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('BAACTV', '1362A7CFD659B972E043814FD881B972', '1', 'N', 'Budget Adjustements Allowed')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('BASEAD', '1362A7CFD65AB972E043814FD881B972', '1', 'N', 'Base Adjustments Allowed')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('BCACTV', '1362A7CFD65BB972E043814FD881B972', '1', 'N', 'Budget Construction Active')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('BCGENE', '1362A7CFD65CB972E043814FD881B972', '1', 'N', 'Budget Construction Genesis Executing')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('BCUPDT', '1362A7CFD65EB972E043814FD881B972', '1', 'N', 'Budget Construction Updates Allowed')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('BSSYNC', '1362A7CFD65DB972E043814FD881B972', '1', 'N', 'Batch Sync BC with PeopleSoft')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('CSFUPD', '1362A7CFD65FB972E043814FD881B972', '1', 'N', 'Calculated Salary Found Updates Allowed')
/

INSERT INTO FP_FUNC_CTRL_CD_T (FS_FUNC_CTRL_CD, OBJ_ID, VER_NBR, FS_FUNC_DFLT_IND, FS_FUNC_DESC) VALUES ('PSSYNC', '1362A7CFD660B972E043814FD881B972', '1', 'N', 'Synchronize BC with PeopleSoft')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('FP_FUNC_CTRL_CD_T', 'ole', 'bootstrap_other_data.xml', NOW(), 5, '7:6145e4afe32490ed2da78282026a92ce', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset bootstrap_other_data.xml::SH_ACCT_PERIOD_T::ole
INSERT INTO SH_ACCT_PERIOD_T (UNIV_FISCAL_YR, UNIV_FISCAL_PRD_CD, OBJ_ID, VER_NBR, UNIV_FISCAL_PRD_NM, ROW_ACTV_IND, BDGT_ROLLOVER_CD, UNIV_FSCPD_END_DT) VALUES ('2012', '01', '788d325b-ec82-40b1-87a4-463c8bb7ea3', '2', 'JULY 2011', 'Y', 'N', '110731000000')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('SH_ACCT_PERIOD_T', 'ole', 'bootstrap_other_data.xml', NOW(), 6, '7:b1c38c986218a3433ed2d604378405f8', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Changeset bootstrap_other_data.xml::ALRT_DOC_TYP_T::ole
INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('1', 'DLVRY_TYP_T', 'Request Type Document', 'org.kuali.ole.deliver.bo.OleDeliverRequestType', 'Y', '2', '1', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('2', 'OLE_BRSMD', 'Bibiliographic Record Status Document', 'org.kuali.ole.describe.bo.OleBibliographicRecordStatus', 'Y', '2', '2', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('3', 'OLE_GLBLY_PRCT_FLD', 'Protected Fields Document', 'org.kuali.ole.select.bo.OleGloballyProtectedField', 'Y', '2', '3', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('4', 'OLE_UPMD', 'Preferences of Bib Import Document', 'org.kuali.ole.describe.bo.ImportBibUserPreferences', 'Y', '2', '4', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('5', 'OLE_SSMD', 'Call Number Type Document', 'org.kuali.ole.describe.bo.OleShelvingScheme', 'Y', '2', '5', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('6', 'OLE_TYPO', 'OwnerShip Type Document', 'org.kuali.ole.describe.bo.OleTypeOfOwnership', 'Y', '2', '6', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('7', 'OLE_ITAS', 'Item Availability Status Document', 'org.kuali.ole.describe.bo.OleItemAvailableStatus', 'Y', '2', '7', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('8', 'OLE_IITMD', 'Item Type Document', 'org.kuali.ole.describe.bo.OleInstanceItemType', 'Y', '2', '8', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('9', 'OLE_RSMD', 'Receipt Status Document', 'org.kuali.ole.describe.bo.OleReceiptStatus', 'Y', '2', '9', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('10', 'OLE_STSRC', 'Statistical Search Codes Document', 'org.kuali.ole.describe.bo.OleStatisticalSearchingCodes', 'Y', '2', '10', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('11', 'OLE_FUND_CD_MD', 'Fund Code Document', 'org.kuali.ole.coa.businessobject.OleFundCode', 'Y', '2', '11', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('12', 'BarcodeStatusMaintenanceDocument', 'Barcode Status Document', 'org.kuali.ole.deliver.bo.BarcodeStatus', 'Y', '2', '12', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('13', 'OLE_DLVR_CAL_DOC', 'Calendar Document', 'org.kuali.ole.deliver.calendar.bo.OleCalendar', 'Y', '2', '13', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('14', 'OLE_CAL_EXP_TYP', 'Calendar Exception Type Document ', 'org.kuali.ole.deliver.calendar.bo.OleCalendarExceptionType', 'Y', '2', '14', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('15', 'OLE_CLNDR_GRP', 'Calendar Group Document', 'org.kuali.ole.deliver.calendar.bo.OleCalendarGroup', 'Y', '2', '15', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('16', 'OLE_CIRC_DESK_MD', 'Circulation Desk Document', 'org.kuali.ole.deliver.bo.OleCirculationDesk', 'Y', '2', '16', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('17', 'OLE_COPY_FORMAT_TYPE', 'Copy Format Type Document', 'org.kuali.ole.deliver.bo.CopyFormat', 'Y', '2', '17', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('18', 'OLE_FDD', 'Due Date Document', 'org.kuali.ole.deliver.bo.OleFixedDueDate', 'Y', '2', '18', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('19', 'OLE_LMD', 'Location Document', 'org.kuali.ole.describe.bo.OleLocation', 'Y', '2', '19', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('20', 'OLE_LLMD', 'Location Level Document', 'org.kuali.ole.describe.bo.OleLocationLevel', 'Y', '2', '20', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('21', 'OleAddressSourceMaintenanceDocument', 'Address Source Document', 'org.kuali.ole.deliver.bo.OleAddressSourceBo', 'Y', '2', '21', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('22', 'OLE_FEE_TYP', 'Fee Types Document', 'org.kuali.ole.deliver.bo.OleFeeType', 'Y', '2', '22', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('23', 'OleSourceMaintenanceDocument', 'Patron Source Document', 'org.kuali.ole.deliver.bo.OleSourceBo', 'Y', '2', '23', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('24', 'OLE_PAY_STAT', 'Payment Status Document', 'org.kuali.ole.deliver.bo.OlePaymentStatus', 'Y', '2', '24', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('25', 'OleStatisticalCategoryMaintenanceDocument', 'Statistical Category Document', 'org.kuali.ole.deliver.bo.OleStatisticalCategoryBo', 'Y', '2', '25', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('26', 'OleBorrowerTypeMaintenanceDocument', 'Borrower Type Document', 'org.kuali.ole.deliver.bo.OleBorrowerType', 'Y', '2', '26', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('27', 'OLE_ACCESS_LOC', 'Access Location Document', 'org.kuali.ole.select.bo.OLEAccessLocation', 'Y', '2', '27', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('28', 'OLE_ERES_ACCESS_MD', 'EResource Access  Document for Activation', 'org.kuali.ole.select.bo.OLEEResourceAccessActivation', 'Y', '2', '28', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('29', 'OLE_ACCESS_TYPE', 'Access  Type Document ', 'org.kuali.ole.select.bo.OLEAccessType', 'Y', '2', '29', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('30', 'OLE_AGR_DOC_TYP_MD', 'Agreement Type Document ', 'org.kuali.ole.select.bo.OleAgreementDocType', 'Y', '2', '30', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('31', 'OLE_AGR_MTH_MD', 'Agreement Method Document ', 'org.kuali.ole.select.bo.OleAgreementMethod', 'Y', '2', '31', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('32', 'OLE_AGR_STAT_MD', 'Agreement Status Document ', 'org.kuali.ole.select.bo.OleAgreementStatus', 'Y', '2', '32', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('33', 'OLE_AUTHTYPE', 'Authentication Type Document ', 'org.kuali.ole.select.bo.OLEAuthenticationType', 'Y', '2', '33', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('34', 'OLE_CONTENT_TYPE', 'Content Type Document ', 'org.kuali.ole.select.bo.OLEContentType', 'Y', '2', '34', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('35', 'OLE_E_RES_STATUS', 'EResource Status Document ', 'org.kuali.ole.select.bo.OLEEResourceStatus', 'Y', '2', '35', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('36', 'OLE_AGR_TYP_MD', 'Agreement Type Document ', 'org.kuali.ole.select.bo.OleAgreementType', 'Y', '2', '36', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('37', 'OLE_CUR_LOC', 'License Request Location Document ', 'org.kuali.ole.select.bo.OleLicenseRequestLocation', 'Y', '2', '37', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('38', 'OLE_LRS_MD', 'License Request Status Document ', 'org.kuali.ole.select.bo.OleLicenseRequestStatus', 'Y', '2', '38', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('39', 'OLE_LIC_REQS_TYP', 'License Request Type Document ', 'org.kuali.ole.select.bo.OleLicenseRequestType', 'Y', '2', '39', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('40', 'OLE_MATERIAL_TYPE', 'Material Type Document', 'org.kuali.ole.select.bo.OLEMaterialType', 'Y', '2', '40', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('41', 'OLE_PACK_SCOPE', 'Package Scope Document', 'org.kuali.ole.select.bo.OLEPackageScope', 'Y', '2', '41', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('42', 'OLE_PACK_TYPE', 'Package Type Document', 'org.kuali.ole.select.bo.OLEPackageType', 'Y', '2', '42', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('43', 'OLE_PAYMENT_TYPE', 'Payment Type Document', 'org.kuali.ole.select.bo.OLEPaymentType', 'Y', '2', '43', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('44', 'OLE_REQUEST_PRIORITY', 'Request Priority Document', 'org.kuali.ole.select.bo.OLERequestPriority', 'Y', '2', '44', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('45', 'OLE_SUBSCRIPTION_STATUS', 'Ole Subscription Status Document', 'org.kuali.ole.select.bo.OleSubscriptionStatus', 'Y', '2', '45', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('46', 'OLE_MOB_ACC', 'Ole Mobile Access Document', 'org.kuali.ole.select.bo.OLEMobileAccess', 'Y', '2', '46', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('47', 'OLE_MARC_REC_SRC_TYP', 'Marc Record Source Type Document', 'org.kuali.ole.select.bo.OLEMarcRecordSourceType', 'Y', '2', '47', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('48', 'OLE_PRBLM_TYP', 'Problem Type Document', 'org.kuali.ole.select.bo.OLEProblemType', 'Y', '2', '48', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('49', 'OLE_ERES_PLTFRM_EVNT_TYP', 'Event Type Document', 'org.kuali.ole.select.bo.OLEEResPltfrmEventType', 'Y', '2', '49', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('50', 'OLE_ACC_ACTV_CONFG_MD', 'Access Activation Document', 'org.kuali.ole.select.bo.OLEAccessActivationConfiguration', 'Y', '2', '50', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('51', 'OLE_PLTFRM_STAT', 'Platform Status Document', 'org.kuali.ole.select.bo.OLEPlatformStatus', 'Y', '2', '51', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('52', 'OLE_PLTFRM_ADMIN_URL_TYPE', 'Platform Admin URL Type Document', 'org.kuali.ole.select.bo.OLEPlatformAdminUrlType', 'Y', '2', '52', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('53', 'OLE_ALERT_EVENT', 'Alert Event Document', 'org.kuali.ole.alert.bo.AlertEvent', 'Y', '2', '53', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('54', 'OLE_ALERT', 'Alert Document', 'org.kuali.ole.alert.bo.AlertDocument', 'Y', '2', '54', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('55', 'OLE_CNCL_RSN_MD', 'Cancellation Reason  Document', 'org.kuali.ole.select.bo.OLECancellationReason', 'Y', '2', '55', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('56', 'OLE_DONOR', 'Donor  Document', 'org.kuali.ole.select.bo.OLEDonor', 'Y', '2', '56', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('57', 'OLE_PURPOSE', 'Purchase Order Purpose Document', 'org.kuali.ole.select.bo.OlePurchaseOrderPurpose', 'Y', '2', '57', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('58', 'OLE_DLVR_REQS', 'Deliver Request Document', 'org.kuali.ole.deliver.bo.OleDeliverRequestBo', 'Y', '2', '58', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('59', 'OLE_PTRN', 'Patron Document', 'org.kuali.ole.deliver.bo.OlePatronDocument', 'Y', '2', '59', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('60', 'LicenseRequestDocument', 'License Request Document', 'org.kuali.ole.select.bo.OleLicenseRequestBo', 'Y', '2', '60', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('61', 'OLE_CHKLST', 'Check List Document', 'org.kuali.ole.select.bo.OleCheckListBo', 'Y', '2', '61', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('62', 'OLE_PRQS', 'Invoice Document', 'org.kuali.ole.krad.transaction.documents.OLEInvoiceDocument', 'Y', '2', '62', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('63', 'OLE_ERS_DOC', 'EResource Record Document', 'org.kuali.ole.select.document.OLEEResourceRecordDocument', 'Y', '2', '63', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('64', 'OLE_PLTFRM_DOC', 'EResource Record Document', 'org.kuali.ole.select.document.OLEPlatformRecordDocument', 'Y', '2', '64', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('65', 'OLE_SER_RECV_REC', 'Serial Receiving Document', 'org.kuali.ole.select.bo.OLESerialReceivingDocument', 'Y', '2', '65', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('66', 'OLE_BCH_PRCS_PRFL_DOC', 'Batch Process Definition Document', 'org.kuali.ole.batch.document.OLEBatchProcessDefinitionDocument', 'Y', '2', '66', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('67', 'OLE_BTCH_PRCS_JOB', 'Batch Process Job Details Document', 'org.kuali.ole.batch.bo.OLEBatchProcessJobDetailsBo', 'Y', '2', '67', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('68', 'OLE_BTCH_PRCS_PRFL', 'Batch Process Profile Document', 'org.kuali.ole.batch.bo.OLEBatchProcessProfileBo', 'Y', '2', '68', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('69', 'DLVRY_BATCH_T', 'Batch Job Document', 'org.kuali.ole.deliver.bo.OleBatchJobBo', 'Y', '2', '69', '1')
/

INSERT INTO ALRT_DOC_TYP_T (ALRT_DOC_TYP_ID, ALRT_DOC_TYP_NAME, ALRT_DOC_TYP_DESC, ALRT_DOC_CLASS, ACTV_IND, ALRT_REM_INTERVAL, OBJ_ID, VER_NBR) VALUES ('70', 'OLE_EDMD', 'External Data Source Configuration Document', 'org.kuali.ole.describe.bo.ExternalDataSourceConfig', 'Y', '2', '70', '1')
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('ALRT_DOC_TYP_T', 'ole', 'bootstrap_other_data.xml', NOW(), 7, '7:ea47c1595715e146fd16ad599353e9c8', 'loadData', '', 'EXECUTED', '3.2.0')
/

--  Release Database Lock
--  Release Database Lock
