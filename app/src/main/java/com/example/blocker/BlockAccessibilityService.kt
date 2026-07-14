package com.example.blocker

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.view.accessibility.AccessibilityEvent
import android.widget.Toast
import java.util.Calendar

class BlockAccessibilityService : AccessibilityService() {

    // רשימת אפליקציות לחסימה (חבילות לדוגמה)
    private val blockedPackages = listOf(
        "com.android.chrome",      // כרום
        "com.google.android.youtube" // יוטיוב
    )

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
            val packageName = event.packageName?.toString() ?: return

            // בדיקה האם האפליקציה שנפתחה נמצאת ברשימה השחורה
            if (blockedPackages.contains(packageName)) {
                
                // כאן נכנס מנגנון השעות והימים:
                if (shouldBlockNow()) {
                    // חזרה למסך הבית של הטלפון באופן מיידי
                    val startMain = Intent(Intent.ACTION_MAIN).apply {
                        addCategory(Intent.CATEGORY_HOME)
                        flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    }
                    startActivity(startMain)
                    
                    Toast.makeText(applicationContext, "אפליקציה זו חסומה כעת!", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    // פונקציה לבדיקת שעות חסימה (למשל: חסימה בשעות הלילה או שעות מוגדרות)
    private fun shouldBlockNow(): Boolean {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        
        // כרגע חוסם תמיד לצורך בדיקה. אפשר לשנות לטווח שעות מסוים, למשל:
        // return hour >= 22 || hour < 6 (חסימה בין 10 בלילה ל-6 בבוקר)
        return true 
    }

    override fun onInterrupt() {
        // נקרא כאשר השירות מופרע
    }
}
