TRUNCATE TABLE OLE_LIC_RQMT_T
/
INSERT INTO OLE_LIC_RQMT_T (LIC_RQMT_CD,LIC_RQMT_DESC,OBJ_ID,VER_NBR,ACT_IND)
  VALUES ('ILR','Initiate License Request ','1',1.0,'Y')
/
INSERT INTO OLE_LIC_RQMT_T (LIC_RQMT_CD,LIC_RQMT_DESC,OBJ_ID,VER_NBR,ACT_IND)
  VALUES ('LRC','License Request Complete ','3',1.0,'Y')
/
INSERT INTO OLE_LIC_RQMT_T (LIC_RQMT_CD,LIC_RQMT_DESC,OBJ_ID,VER_NBR,ACT_IND)
  VALUES ('NF','Negotiation Failed ','4',1.0,'Y')
/
INSERT INTO OLE_LIC_RQMT_T (LIC_RQMT_CD,LIC_RQMT_DESC,OBJ_ID,VER_NBR,ACT_IND)
  VALUES ('NLR','No License Required','2',1.0,'Y')
/