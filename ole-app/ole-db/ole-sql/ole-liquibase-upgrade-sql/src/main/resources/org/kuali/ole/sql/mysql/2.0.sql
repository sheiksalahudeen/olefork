--  *********************************************************************
--  Update Database Script
--  *********************************************************************
--  Change Log: org/kuali/ole/2.0/db.changelog-20141229.xml
--  *********************************************************************

--  Lock Database
--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_PKG_T::ole
CREATE TABLE ole.OLE_GOKB_PKG_T (GOKB_PKG_ID INT NULL, PKG_NAME VARCHAR(1000) NULL, VARIANT_NAME VARCHAR(4000) NULL, PKG_STATUS VARCHAR(20) NULL, PKG_SCOPE VARCHAR(20) NULL, BREAKABLE VARCHAR(10) NULL, FXD VARCHAR(10) NULL, AVLBLE VARCHAR(10) NULL, DATE_CREATED date NULL, DATE_UPDATED date NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_PKG_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 1, '7:edf59a3f8138bb2ec7caeb0cd9cef297', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_PKG_T_PK::ole
ALTER TABLE ole.OLE_GOKB_PKG_T ADD PRIMARY KEY (GOKB_PKG_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_PKG_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 2, '7:5e78675e903727d7b8951e3e7c356b09', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_TIPP_T::ole
CREATE TABLE ole.OLE_GOKB_TIPP_T (GOKB_TIPP_ID INT NULL, GOKB_PKG_ID INT NULL, GOKB_TITLE_ID INT NULL, GOKB_PLTFRM_ID INT NULL, TIPP_STATUS VARCHAR(100) NULL, STATUS_REASON VARCHAR(300) NULL, STRT_DT date NULL, STRT_VOL VARCHAR(100) NULL, STRT_ISSUE VARCHAR(100) NULL, END_DT date NULL, END_VOL VARCHAR(100) NULL, END_ISSUE VARCHAR(100) NULL, EMBARGO VARCHAR(50) NULL, PLTFRM_HOST_URL VARCHAR(300) NULL, DATE_CREATED date NULL, DATE_UPDATED date NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_TIPP_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 3, '7:4dafadcf4969bd4e6418ad659bf253cf', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_TIPP_T_PK::ole
ALTER TABLE ole.OLE_GOKB_TIPP_T ADD PRIMARY KEY (GOKB_TIPP_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_TIPP_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 4, '7:920f52704aaf17611883afa292cd9655', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_TITLE_T::ole
CREATE TABLE ole.OLE_GOKB_TITLE_T (GOKB_TITLE_ID INT NULL, TITLE_NAME VARCHAR(500) NULL, VARIANT_NAME VARCHAR(4000) NULL, MEDIUM VARCHAR(50) NULL, PURE_QA VARCHAR(50) NULL, TI_ISSN_ONLINE VARCHAR(50) NULL, TI_ISSN_PRNT VARCHAR(50) NULL, TI_ISSN_L VARCHAR(50) NULL, OCLC_NUM INT NULL, TI_DOI VARCHAR(50) NULL, TI_PROPRIETARY_ID INT NULL, TI_SUNCAT VARCHAR(50) NULL, TI_LCCN VARCHAR(50) NULL, PUBLSHR_ID INT NULL, IMPRINT INT NULL, DATE_CREATED date NULL, DATE_UPDATED date NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_TITLE_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 5, '7:49dfdfff17e0160134908fe4de79891a', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_TITLE_T_PK::ole
ALTER TABLE ole.OLE_GOKB_TITLE_T ADD PRIMARY KEY (GOKB_TITLE_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_TITLE_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 6, '7:00edf027b12b4c5a29a6aea7f1f84fb9', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_PLTFRM_T::ole
CREATE TABLE ole.OLE_GOKB_PLTFRM_T (GOKB_PLTFRM_ID INT NULL, PLTFRM_NAME VARCHAR(500) NULL, PLTFRM_STATUS VARCHAR(100) NULL, PLTFRM_PRVDR_ID INT NULL, AUTH VARCHAR(50) NULL, SOFTWARE_PLTFRM VARCHAR(50) NULL, DATE_CREATED date NULL, DATE_UPDATED date NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_PLTFRM_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 7, '7:6e6e6889f08ef1aeda3f365592d9fcc6', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_PLTFRM_T_PK::ole
ALTER TABLE ole.OLE_GOKB_PLTFRM_T ADD PRIMARY KEY (GOKB_PLTFRM_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_PLTFRM_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 8, '7:5ded2aa3468dfde9aa47a9c971060a16', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_ORG_T::ole
CREATE TABLE ole.OLE_GOKB_ORG_T (GOKB_ORG_ID INT NULL, ORG_NAME VARCHAR(1000) NULL, VARIANT_NAME VARCHAR(4000) NULL, DATE_CREATED date NULL, DATE_UPDATED date NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_ORG_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 9, '7:83142384cd09d875a4385c1acacfa749', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_ORG_T_PK::ole
ALTER TABLE ole.OLE_GOKB_ORG_T ADD PRIMARY KEY (GOKB_ORG_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_ORG_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 10, '7:5e1e76142258caa8af2c621bb1f2422d', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_UPDATE_LOG_T::ole
CREATE TABLE ole.OLE_GOKB_UPDATE_LOG_T (ID INT NULL, NUM_PKGS INT NULL, NUM_TIPPS INT NULL, NUM_TITLES INT NULL, NUM_ORGS INT NULL, NUM_PLTFRMS INT NULL, USER_NM VARCHAR(100) NULL, STATUS VARCHAR(20) NULL, START_TIME date NULL, END_TIME date NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_UPDATE_LOG_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 11, '7:972d3f7bc8a958636cbad3b2a6b78501', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_UPDATE_LOG_T_PK::ole
ALTER TABLE ole.OLE_GOKB_UPDATE_LOG_T ADD PRIMARY KEY (ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_UPDATE_LOG_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 12, '7:d39ec8b6f486d38e3881b8b2ec5d51a7', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_ORG_ROLE_T::ole
CREATE TABLE ole.OLE_GOKB_ORG_ROLE_T (GOKB_ORG_ROLE_ID INT NULL, GOKB_ORG_ID INT NULL, ROLE VARCHAR(100) NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_ORG_ROLE_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 13, '7:1229c2beb1ab4c324edc169a78fe6f1a', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_ORG_ROLE_T_PK::ole
ALTER TABLE ole.OLE_GOKB_ORG_ROLE_T ADD PRIMARY KEY (GOKB_ORG_ROLE_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_ORG_ROLE_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 14, '7:e65f5a4dc037fcc12a8f12b8115cd335', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_REVIEW_T::ole
CREATE TABLE ole.OLE_GOKB_REVIEW_T (GOKB_REVIEW_ID INT NULL, REVIEW_DT date NULL, E_RESOURCE_ID VARCHAR(100) NULL, E_RESOURCE_NAME VARCHAR(100) NULL, TYPE VARCHAR(100) NULL, DETAILS VARCHAR(1000) NULL, GOKB_TIPP_ID INT NULL, VER_NBR DECIMAL(8) NULL, OBJ_ID VARCHAR(36) NULL)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_REVIEW_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 15, '7:b4f5f5ba5cdf62fabf49bfeae6a4f2a5', 'createTable', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_GOKB_REVIEW_T_PK::ole
ALTER TABLE ole.OLE_GOKB_REVIEW_T ADD PRIMARY KEY (GOKB_REVIEW_ID)
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_GOKB_REVIEW_T_PK', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 16, '7:3ca38504bc60b50072105016a45174e9', 'addPrimaryKey', '', 'EXECUTED', '3.2.0')
/

--  Changeset org/kuali/ole/2.0/db.changelog-20141229.xml::OLE_DS_DOC_FIELD_T::ole
INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level1Location','390','8','3','Y','N','N','Y','N','Level1Location_display','390','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level2Location','391','8','3','Y','N','N','Y','N','Level2Location_display','391','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','392','8','3','Y','N','N','Y','N','Level3Location_display','392','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level4Location','393','8','3','Y','N','N','Y','N','Level4Location_display','393','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level5Location','394','8','3','Y','N','N','Y','N','Level5Location_display','394','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level1Location','395','9','4','Y','N','N','Y','N','Level1Location_display','395','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','396','9','4','Y','N','N','Y','N','Level2Location_display','396','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','397','9','4','Y','N','N','Y','N','Level3Location_display','397','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level4Location','398','9','4','Y','N','N','Y','N','Level4Location_display','398','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level5Location','399','9','4','Y','N','N','Y','N','Level5Location_display','399','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level1Location','410','10','5','Y','N','N','Y','N','Level1Location_display','410','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level2Location','411','10','5','Y','N','N','Y','N','Level2Location_display','411','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','412','10','5','Y','N','N','Y','N','Level3Location_display','412','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level4Location','413','10','5','Y','N','N','Y','N','Level4Location_display','413','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level5Location','414','10','5','Y','N','N','Y','N','Level5Location_display','414','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level1Location','404','8','3','N','N','N','Y','Y','Level1Location_search','404','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level2Location','400','8','3','N','N','N','Y','Y','Level2Location_search','400','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','401','8','3','N','N','N','Y','Y','Level3Location_search','401','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level4Location','402','8','3','N','N','N','Y','Y','Level4Location_search','402','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level5Location','403','8','3','N','N','N','Y','Y','Level5Location_search','404','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level1Location','405','9','4','N','N','N','Y','Y','Level1Location_search','405','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level2Location','406','9','4','N','N','N','Y','Y','Level2Location_search','406','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','407','9','4','N','N','N','Y','Y','Level3Location_search','407','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level4Location','408','9','4','N','N','N','Y','Y','Level4Location_search','408','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level5Location','409','9','4','N','N','N','Y','Y','Level5Location_search','409','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level1Location','415','10','5','N','N','N','Y','Y','Level1Location_search','415','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level2Location','416','10','5','N','N','N','Y','Y','Level2Location_search','416','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level3Location','417','10','5','N','N','N','Y','Y','Level3Location_search','417','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level4Location','418','10','5','N','N','N','Y','Y','Level4Location_search','418','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Level5Location','419','10','5','N','N','N','Y','Y','Level5Location_search','419','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Item Barcode','420','1','1','N','N','N','N','Y','ItemBarcode_search','420','1')
/

INSERT INTO OLE_DS_DOC_FIELD_T (DISPLAY_LABEL,DOC_FIELD_ID,DOC_FORMAT_ID,DOC_TYPE_ID,IS_DISPLAY,IS_EXPORT,IS_FACET,IS_GLOBAL_EDIT,IS_SEARCH,NAME,OBJ_ID,VER_NBR) VALUES ('Uri','421','1','1','N','N','N','N','Y','Uri_search','421','1')
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=157
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=162
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=243
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=248
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=250
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=257
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=185
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=187
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y',DISPLAY_LABEL="Donor Code" WHERE DOC_FIELD_ID=292
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=293
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y',DISPLAY_LABEL="Fast Add" WHERE DOC_FIELD_ID=294
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=298
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y',DISPLAY_LABEL="Claims Return Flag" WHERE DOC_FIELD_ID=299
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=300
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=301
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=180
/

UPDATE OLE_DS_DOC_FIELD_T SET IS_SEARCH='Y' WHERE DOC_FIELD_ID=284
/

INSERT INTO DATABASECHANGELOG (ID, AUTHOR, FILENAME, DATEEXECUTED, ORDEREXECUTED, MD5SUM, DESCRIPTION, COMMENTS, EXECTYPE, LIQUIBASE) VALUES ('OLE_DS_DOC_FIELD_T', 'ole', 'org/kuali/ole/2.0/db.changelog-20141229.xml', NOW(), 17, '7:9e4fc28cde26b16cb098b0546e47dbc2', 'sql (x49)', '', 'EXECUTED', '3.2.0')
/

--  Release Database Lock
--  Release Database Lock