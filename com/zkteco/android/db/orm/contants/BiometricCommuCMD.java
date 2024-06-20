package com.zkteco.android.db.orm.contants;

public class BiometricCommuCMD {
    public static final int BIOMETRIC_CMD_BASE = 3001;
    public static final int BIOMETRIC_CMD_BIO = 3301;
    public static final int BIOMETRIC_CMD_BIO_ADD = 3308;
    public static final int BIOMETRIC_CMD_BIO_DEL = 3309;
    public static final int BIOMETRIC_CMD_BIO_FREE = 3303;
    public static final int BIOMETRIC_CMD_BIO_GET_PARAMETER = 3305;
    public static final int BIOMETRIC_CMD_BIO_IDENTIFY = 3306;
    public static final int BIOMETRIC_CMD_BIO_INIT = 3302;
    public static final int BIOMETRIC_CMD_BIO_RELOAD_TEMPLATE = 3326;
    public static final int BIOMETRIC_CMD_BIO_SET_PARAMETER = 3304;
    public static final int BIOMETRIC_CMD_BIO_UPDATE = 3310;
    public static final int BIOMETRIC_CMD_BIO_VERIFY = 3307;
    public static final int BIOMETRIC_CMD_FACE = 3031;
    public static final int BIOMETRIC_CMD_FACE_ADD = 3038;
    public static final int BIOMETRIC_CMD_FACE_DEL = 3039;
    public static final int BIOMETRIC_CMD_FACE_FREE = 3033;
    public static final int BIOMETRIC_CMD_FACE_GET_PARAMETER = 3035;
    public static final int BIOMETRIC_CMD_FACE_IDENTIFY = 3036;
    public static final int BIOMETRIC_CMD_FACE_INIT = 3032;
    public static final int BIOMETRIC_CMD_FACE_RELOAD_TEMPLATE = 3056;
    public static final int BIOMETRIC_CMD_FACE_SET_PARAMETER = 3034;
    public static final int BIOMETRIC_CMD_FACE_UPDATE = 3040;
    public static final int BIOMETRIC_CMD_FACE_VERIFY = 3037;
    public static final int BIOMETRIC_CMD_FP_ADD = 3010;
    public static final int BIOMETRIC_CMD_FP_COMBINE = 3014;
    public static final int BIOMETRIC_CMD_FP_DEL = 3011;
    public static final int BIOMETRIC_CMD_FP_ENROLL = 3007;
    public static final int BIOMETRIC_CMD_FP_EXTRACT = 3013;
    public static final int BIOMETRIC_CMD_FP_EXTRACT_FROM_FILE = 3018;
    public static final int BIOMETRIC_CMD_FP_FREE = 3004;
    public static final int BIOMETRIC_CMD_FP_GET_PARAMETER = 3006;
    public static final int BIOMETRIC_CMD_FP_GET_TEMPLATE = 3016;
    public static final int BIOMETRIC_CMD_FP_IDENTIFY = 3008;
    public static final int BIOMETRIC_CMD_FP_INIT = 3003;
    public static final int BIOMETRIC_CMD_FP_PACK = 3015;
    public static final int BIOMETRIC_CMD_FP_RELOAD_TEMPLATE = 3026;
    public static final int BIOMETRIC_CMD_FP_SET_PARAMETER = 3005;
    public static final int BIOMETRIC_CMD_FP_SPLIT = 3017;
    public static final int BIOMETRIC_CMD_FP_UPDATE = 3012;
    public static final int BIOMETRIC_CMD_FP_VEIN = 3091;
    public static final int BIOMETRIC_CMD_FP_VEIN_ADD = 3098;
    public static final int BIOMETRIC_CMD_FP_VEIN_DEL = 3099;
    public static final int BIOMETRIC_CMD_FP_VEIN_EXTRACT = 3101;
    public static final int BIOMETRIC_CMD_FP_VEIN_FREE = 3093;
    public static final int BIOMETRIC_CMD_FP_VEIN_GET_PARAMETER = 3095;
    public static final int BIOMETRIC_CMD_FP_VEIN_IDENTIFY = 3096;
    public static final int BIOMETRIC_CMD_FP_VEIN_INIT = 3092;
    public static final int BIOMETRIC_CMD_FP_VEIN_RELOAD_TEMPLATE = 3116;
    public static final int BIOMETRIC_CMD_FP_VEIN_SET_PARAMETER = 3094;
    public static final int BIOMETRIC_CMD_FP_VEIN_UPDATE = 3100;
    public static final int BIOMETRIC_CMD_FP_VEIN_VERIFY = 3097;
    public static final int BIOMETRIC_CMD_FP_VERIFY = 3009;
    public static final int BIOMETRIC_CMD_GET_STATUS = 3902;
    public static final int BIOMETRIC_CMD_VEIN = 3061;
    public static final int BIOMETRIC_CMD_VEIN_ADD = 3068;
    public static final int BIOMETRIC_CMD_VEIN_COMBINE = 3072;
    public static final int BIOMETRIC_CMD_VEIN_DEL = 3069;
    public static final int BIOMETRIC_CMD_VEIN_EXTRACT = 3071;
    public static final int BIOMETRIC_CMD_VEIN_FREE = 3063;
    public static final int BIOMETRIC_CMD_VEIN_GET_PARAMETER = 3065;
    public static final int BIOMETRIC_CMD_VEIN_IDENTIFY = 3066;
    public static final int BIOMETRIC_CMD_VEIN_INIT = 3062;
    public static final int BIOMETRIC_CMD_VEIN_RELOAD_TEMPLATE = 3086;
    public static final int BIOMETRIC_CMD_VEIN_SET_PARAMETER = 3064;
    public static final int BIOMETRIC_CMD_VEIN_UPDATE = 3070;
    public static final int BIOMETRIC_CMD_VEIN_VERIFY = 3067;
    public static final int BIOMETRIC_COMMON_CMD = 3901;
    public static final int BIOMETRIC_SVR_BUSY = -1;
    public static final int BIOMETRIC_SVR_OK = 0;
    public static final int BIOMETRIC_UPDATE_FP_TEMPLATE = 1;
    public static final int BIOMETRIC_UPDATE_FP_VALID = 2;
    public static final int BioTechnology_FACE = 2;
    public static final int BioTechnology_FINGERPRINT = 1;
    public static final int BioTechnology_FINGER_VEIN = 7;
    public static final int BioTechnology_GENERIC = 0;
    public static final int BioTechnology_HAND_GEOMETRY = 6;
    public static final int BioTechnology_IRIS = 4;
    public static final int BioTechnology_PALM_VEIN = 8;
    public static final int BioTechnology_RETINA = 5;
    public static final int BioTechnology_VOICE = 3;
    public static final int FACE_ID_VERIFICATION_MODE = 1;
    public static final int FACE_VARIOUS_VERIFICATION_MODE = 0;
    public static final String FIELD_DESC_ERROR = "Error";
    public static final String FIELD_DESC_FV_TEMPLATE = "FVeinTemplate";
    public static final String FIELD_DESC_TEMPLATE = "TMP";
    public static final String FIELD_DESC_TMP_COUNT = "TMPCOUNT";
    public static final String FIELD_DESC_TMP_ID = "ID";
    public static final String FIELD_DESC_TMP_INDEX = "FID";
    public static final String FIELD_DESC_TMP_PIN = "PIN";
    public static final String FIELD_DESC_TMP_QUALITY = "QUALITY";
    public static final String FIELD_DESC_TMP_SCORE = "SCORE";
    public static final String FIELD_DESC_TMP_SIZE = "SIZE";
    public static final int FP_ID_VERIFICATION_MODE = 1;
    public static final int FP_IMAGE_LEN = 153600;
    public static final int FP_TEMPLATE_LEN = 2048;
    public static final int FP_VARIOUS_VERIFICATION_MODE = 0;
    public static final int FV_MAX_TEMPLATE_SIZE = 1024;
    public static final int FV_TEMPLATE_COUNT = 3;
    public static final int IS_DURESS_FINGER_FLAG = 3;
    public static final int MAX_TEMPLATE_SIZE = 1220;
    public static final int SUB_CMD_BIO_ADD_BY_ID = 4;
    public static final int SUB_CMD_BIO_ADD_DB = 2;
    public static final int SUB_CMD_BIO_ADD_MEM = 1;
    public static final int SUB_CMD_BIO_ADD_MEM_AND_DB = 3;
    public static final int SUB_CMD_BIO_DEL_ALL_DATA = 10;
    public static final int SUB_CMD_BIO_DEL_DB = 2;
    public static final int SUB_CMD_BIO_DEL_MEM = 1;
    public static final int SUB_CMD_BIO_DEL_MEM_AND_DB = 3;
    public static final int SUB_CMD_BIO_GET_ALL_COUNT = 3;
    public static final int SUB_CMD_BIO_GET_BEST_TMP = 5;
    public static final int SUB_CMD_BIO_GET_TMP_QUALITY = 4;
    public static final int SUB_CMD_BIO_GET_USED_COUNT = 6;
    public static final int SUB_CMD_BIO_GET_USER_COUNT = 1;
    public static final int SUB_CMD_BIO_GET_USER_EXIST_ID = 2;
    public static final int SUB_CMD_BIO_IDENTIFY_IMAGE = 1;
    public static final int SUB_CMD_BIO_IDENTIFY_TEMPLATE = 2;
    public static final int SUB_CMD_BIO_UPDATE_DB = 2;
    public static final int SUB_CMD_BIO_UPDATE_MEM = 1;
    public static final int SUB_CMD_BIO_UPDATE_MEM_AND_DB = 3;
    public static final int SUB_CMD_BIO_VERIFY_IMAGE_ID = 1;
    public static final int SUB_CMD_BIO_VERIFY_IMAGE_TEMPLATE = 2;
    public static final int SUB_CMD_BIO_VERIFY_MUL_TEMPLATE = 3;
    public static final int SUB_CMD_BIO_VERIFY_TEMPLATE_ID = 4;
    public static final int SUB_CMD_BIO_VERIFY_TEMPLATE_TEMPLATE = 5;
    public static final int SUB_CMD_FACE_ADD_BY_ID = 4;
    public static final int SUB_CMD_FACE_ADD_DB = 2;
    public static final int SUB_CMD_FACE_ADD_MEM = 1;
    public static final int SUB_CMD_FACE_ADD_MEM_AND_DB = 3;
    public static final int SUB_CMD_FACE_DEL_ALL_DATA = 10;
    public static final int SUB_CMD_FACE_DEL_DB = 2;
    public static final int SUB_CMD_FACE_DEL_MEM = 1;
    public static final int SUB_CMD_FACE_DEL_MEM_AND_DB = 3;
    public static final int SUB_CMD_FACE_GET_ALL_FACE_COUNT = 3;
    public static final int SUB_CMD_FACE_GET_USER_EXIST_FACE_ID = 2;
    public static final int SUB_CMD_FACE_GET_USER_FACE_COUNT = 1;
    public static final int SUB_CMD_FACE_IDENTIFY_IMAGE = 1;
    public static final int SUB_CMD_FACE_IDENTIFY_TEMPLATE = 2;
    public static final int SUB_CMD_FACE_UPDATE_DB = 2;
    public static final int SUB_CMD_FACE_UPDATE_MEM = 1;
    public static final int SUB_CMD_FACE_UPDATE_MEM_AND_DB = 3;
    public static final int SUB_CMD_FACE_VERIFY_IMAGE_ID = 1;
    public static final int SUB_CMD_FACE_VERIFY_IMAGE_TEMPLATE = 2;
    public static final int SUB_CMD_FACE_VERIFY_MUL_TEMPLATE = 3;
    public static final int SUB_CMD_FACE_VERIFY_TEMPLATE_ID = 4;
    public static final int SUB_CMD_FACE_VERIFY_TEMPLATE_TEMPLATE = 5;
    public static final int SUB_CMD_FP_ADD_DB = 2;
    public static final int SUB_CMD_FP_ADD_MEM = 1;
    public static final int SUB_CMD_FP_ADD_MEM_AND_DB = 3;
    public static final int SUB_CMD_FP_DEL_ALL_DATA = 10;
    public static final int SUB_CMD_FP_DEL_DB = 2;
    public static final int SUB_CMD_FP_DEL_MEM = 1;
    public static final int SUB_CMD_FP_DEL_MEM_AND_DB = 3;
    public static final int SUB_CMD_FP_ENROLL_ADD_DB = 7;
    public static final int SUB_CMD_FP_ENROLL_ADD_MEM = 4;
    public static final int SUB_CMD_FP_ENROLL_BEGIN = 1;
    public static final int SUB_CMD_FP_ENROLL_COMBINE = 3;
    public static final int SUB_CMD_FP_ENROLL_DEL_MEM = 8;
    public static final int SUB_CMD_FP_ENROLL_END = 9;
    public static final int SUB_CMD_FP_ENROLL_EXTRACT = 2;
    public static final int SUB_CMD_FP_ENROLL_GET_FPINFO = 6;
    public static final int SUB_CMD_FP_ENROLL_UPDATE = 5;
    public static final int SUB_CMD_FP_GET_ALL_FINGER_COUNT = 3;
    public static final int SUB_CMD_FP_GET_USER_EXIST_FINGER_ID = 2;
    public static final int SUB_CMD_FP_GET_USER_FINGER_COUNT = 1;
    public static final int SUB_CMD_FP_IDENTIFY_IMAGE = 1;
    public static final int SUB_CMD_FP_IDENTIFY_TEMPLATE = 2;
    public static final int SUB_CMD_FP_UPDATE_DB = 2;
    public static final int SUB_CMD_FP_UPDATE_MEM = 1;
    public static final int SUB_CMD_FP_UPDATE_MEM_AND_DB = 3;
    public static final int SUB_CMD_FP_VEIN_ADD_DB = 2;
    public static final int SUB_CMD_FP_VEIN_ADD_MEM = 1;
    public static final int SUB_CMD_FP_VEIN_ADD_MEM_AND_DB = 3;
    public static final int SUB_CMD_FP_VEIN_DEL_ALL_DATA = 10;
    public static final int SUB_CMD_FP_VEIN_DEL_DB = 2;
    public static final int SUB_CMD_FP_VEIN_DEL_MEM = 1;
    public static final int SUB_CMD_FP_VEIN_DEL_MEM_AND_DB = 3;
    public static final int SUB_CMD_FP_VEIN_GET_ALL_VEIN_COUNT = 3;
    public static final int SUB_CMD_FP_VEIN_GET_USER_EXIST_FP_VEIN_ID = 2;
    public static final int SUB_CMD_FP_VEIN_GET_USER_VEIN_COUNT = 1;
    public static final int SUB_CMD_FP_VEIN_IDENTIFY_IMAGE = 1;
    public static final int SUB_CMD_FP_VEIN_IDENTIFY_TEMPLATE = 2;
    public static final int SUB_CMD_FP_VEIN_UPDATE_DB = 2;
    public static final int SUB_CMD_FP_VEIN_UPDATE_MEM = 1;
    public static final int SUB_CMD_FP_VEIN_UPDATE_MEM_AND_DB = 3;
    public static final int SUB_CMD_FP_VEIN_VERIFY_IMAGE_ID = 1;
    public static final int SUB_CMD_FP_VEIN_VERIFY_IMAGE_TEMPLATE = 2;
    public static final int SUB_CMD_FP_VEIN_VERIFY_MUL_TEMPLATE = 3;
    public static final int SUB_CMD_FP_VEIN_VERIFY_TEMPLATE_ID = 4;
    public static final int SUB_CMD_FP_VEIN_VERIFY_TEMPLATE_TEMPLATE = 5;
    public static final int SUB_CMD_FP_VERIFY_IMAGE_ID = 1;
    public static final int SUB_CMD_FP_VERIFY_IMAGE_TEMPLATE = 2;
    public static final int SUB_CMD_FP_VERIFY_MUL_TEMPLATE = 3;
    public static final int SUB_CMD_FP_VERIFY_TEMPLATE_ID = 4;
    public static final int SUB_CMD_VEIN_ADD_DB = 2;
    public static final int SUB_CMD_VEIN_ADD_MEM = 1;
    public static final int SUB_CMD_VEIN_ADD_MEM_AND_DB = 3;
    public static final int SUB_CMD_VEIN_DEL_ALL_DATA = 10;
    public static final int SUB_CMD_VEIN_DEL_DB = 2;
    public static final int SUB_CMD_VEIN_DEL_MEM = 1;
    public static final int SUB_CMD_VEIN_DEL_MEM_AND_DB = 3;
    public static final int SUB_CMD_VEIN_GET_ALL_VEIN_COUNT = 3;
    public static final int SUB_CMD_VEIN_GET_USER_EXIST_VEIN_ID = 2;
    public static final int SUB_CMD_VEIN_GET_USER_VEIN_COUNT = 1;
    public static final int SUB_CMD_VEIN_IDENTIFY_IMAGE = 1;
    public static final int SUB_CMD_VEIN_IDENTIFY_TEMPLATE = 2;
    public static final int SUB_CMD_VEIN_UPDATE_DB = 2;
    public static final int SUB_CMD_VEIN_UPDATE_MEM = 1;
    public static final int SUB_CMD_VEIN_UPDATE_MEM_AND_DB = 3;
    public static final int SUB_CMD_VEIN_VERIFY_IMAGE_ID = 1;
    public static final int SUB_CMD_VEIN_VERIFY_IMAGE_TEMPLATE = 2;
    public static final int SUB_CMD_VEIN_VERIFY_MUL_TEMPLATE = 3;
    public static final int SUB_CMD_VEIN_VERIFY_TEMPLATE_ID = 4;
    public static final int SUB_CMD_VEIN_VERIFY_TEMPLATE_TEMPLATE = 5;
}
