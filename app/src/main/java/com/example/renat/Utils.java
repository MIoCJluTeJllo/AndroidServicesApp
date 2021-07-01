package com.example.renat;

public class Utils {
    static String getWebViewUrl(String device_id){
        return "https://111nb.ru/form.php?table=ezdki&system_hide_module_title=1&system_hide_top_menu=1&system_hide_balance=1&system_hide_api_warnings=1&module_hide_user_filter=1&module_hide_new_ezdka_button=1&device_id=" + device_id;
    }
}
