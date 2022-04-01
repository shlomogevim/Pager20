package com.sg.pager20.utilities

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.airbnb.lottie.LottieAnimationView
import com.sg.pager20.R
import kotlinx.coroutines.CoroutineScope

class UtilCoutine {
    val util=Utility()
    fun createDialog(context: Context, ind: Int) {

       util. logi("UtilCourtin  15 createDialoge   =====> ind=$ind      contex=$context")

        val dialog = Dialog(context)
       util. logi("Utility 35 createDialoge   =====> ind=$ind")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.option_menu1)
        val btn1 = dialog.findViewById<Button>(R.id.btn1_dialog)
        val btn2 = dialog.findViewById<Button>(R.id.btn2_dialog)
        val btn3 = dialog.findViewById<Button>(R.id.btn3_dialog)
        val loti = dialog.findViewById<LottieAnimationView>(R.id.lottie_anim_dialog)
        val dialogText1 = dialog.findViewById<TextView>(R.id.text_dialog1)
        val dialogText2 = dialog.findViewById<TextView>(R.id.text_dialog2)
        val dialogText3 = dialog.findViewById<TextView>(R.id.text_dialog3)
        val dialogText4 = dialog.findViewById<TextView>(R.id.text_dialog4)
        btn1.visibility = View.GONE
        btn2.visibility = View.GONE

        util.logi("Utility48 createDialoge   =====> ind=$ind")

        val arString:ArrayList<String> =getDialogMessage(ind)

       util. logi("Utility 52  createDialoge   =====> ind=$ind")
        dialogText1.text =arString[0]
        dialogText2.text =arString[1]
        dialogText3.text =arString[2]
        dialogText4.text =arString[3]
        btn3.text =arString[4]
        loti.setAnimation(arString[5])
        btn1.setOnClickListener { }
        btn2.setOnClickListener { }
        btn3.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

    private fun getDialogMessage(ind: Int): ArrayList<String> {
        var stMessage1 = ""
        var stMessage2 = ""
        var stMessage3 = ""
        var stMessage4 = ""
        var stBackBtn = ""
        var stAnimation = ""
        if (ind ==2) {
            stMessage1 = "אתה כרגע משתתף אנונימי "
            stMessage2 ="ולכן אין לך אישור לכתוב הערות ,"
            stMessage3 =  "אתה צריך קודם  להיכנס ..."
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור להערות"
            stAnimation="right.json"
        }
        if (ind == 1) {
            stMessage1 = "אתה כרגע משתתף אנונימי "
            stMessage2 ="ולכן לא יעזור לך ללחוץ על צלמית השלח ,"
            stMessage3 =  "אתה צריך קודם להיכנס..."
            stMessage4 = " "
            stBackBtn= "לחץ פה כדי לחזור להערות"
            stAnimation="right.json"
        }
        if (ind == 3) {
            stMessage1 = " לא כתבת כלום בהערה ..."
            stMessage2 = "קודם תכתוב משהו,"
            stMessage3 = "ואחר כך לחץ על שלח ..."
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור להערות"
            stAnimation="right.json"
        }
        if (ind == 4) {
            stMessage1 = " לא הכנסת מייל..."
            stMessage2 = ""
            stMessage3 = ""
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך הכניסה"
            stAnimation="right.json"
        }
        if (ind == 5) {
            stMessage1 = " לא הכנסת סיסמה..."
            stMessage2 = ""
            stMessage3 = ""
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך הכניסה"
            stAnimation="right.json"
        }
        if (ind == 6) {
            stMessage1 = " לא הכנסת שם משתמש..."
            stMessage2 = "זה שם שיזהה אותך"
            stMessage3 = "(יכול להיות פקטיבי)"
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 7) {
            stMessage1 = " לא הכנסת מייל..."
            stMessage2 = ""
            stMessage3 = ""
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 8) {
            stMessage1 = " לא הכנסת סיסמה..."
            stMessage2 = ""
            stMessage3 = ""
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 9) {
            stMessage1 = "חביבי, מישהו כבר משתמש במייל הזה,"
            stMessage2 = "נסה להכניס מייל אחר"
            stMessage3 = ""
            stMessage4 = " "
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 10) {
            stMessage1 = "הסיסמה לא תקינה"
            stMessage2 = "צריך להיות לפחות 6 מספרים"
            stMessage3 = ""
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 11) {
            stMessage1 = "המייל שהכנסת לא תקין ... "
            stMessage2 = "נסה להכניס מייל אחר"
            stMessage3 = "כמובן יכול להיות סתם מייל פקטיבי"
            stMessage4 = " משהוא כמו:        a@bc.com"
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 12) {
            stMessage1 = "השם הזה כבר קיים במערכת ... "
            stMessage2 = "מצא לעצמך שם אחר"
            stMessage3 = ""
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        if (ind == 13) {
            stMessage1 = "מזל טוב ... "
            stMessage2 = "הצלחת להרשם בהצלחה"
            stMessage3 = "ברוך הבא"
            stMessage4 = ""
            stBackBtn= "לחץ פה כדי לחזור למסך ההרשמה"
            stAnimation="right.json"
        }
        return arrayListOf(stMessage1,stMessage2,stMessage3,stMessage4,stBackBtn,stAnimation)
    }

    fun createDialog1(context: Context, ind: Int) {
        util. logi("UtilCourtin  15 createDialoge   =====> ind=$ind      contex=$context")

        val dialog = Dialog(context)
        util. logi("Utility 35 createDialoge   =====> ind=$ind")
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.option_menu1)
        val btn1 = dialog.findViewById<Button>(R.id.btn1_dialog)
        val btn2 = dialog.findViewById<Button>(R.id.btn2_dialog)
        val btn3 = dialog.findViewById<Button>(R.id.btn3_dialog)
        val loti = dialog.findViewById<LottieAnimationView>(R.id.lottie_anim_dialog)
        val dialogText1 = dialog.findViewById<TextView>(R.id.text_dialog1)
        val dialogText2 = dialog.findViewById<TextView>(R.id.text_dialog2)
        val dialogText3 = dialog.findViewById<TextView>(R.id.text_dialog3)
        val dialogText4 = dialog.findViewById<TextView>(R.id.text_dialog4)
        btn1.visibility = View.GONE
        btn2.visibility = View.GONE

        util.logi("Utility48 createDialoge   =====> ind=$ind")

        val arString:ArrayList<String> =getDialogMessage(ind)

        util. logi("Utility 52  createDialoge   =====> ind=$ind")
        dialogText1.text =arString[0]
        dialogText2.text =arString[1]
        dialogText3.text =arString[2]
        dialogText4.text =arString[3]
        btn3.text =arString[4]
        loti.setAnimation(arString[5])
        btn1.setOnClickListener { }
        btn2.setOnClickListener { }
        btn3.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()

    }

}