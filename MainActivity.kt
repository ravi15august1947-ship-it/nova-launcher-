package com.nova.launcher

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : Activity() {
    private val white = Color.rgb(247, 244, 255)
    private val muted = Color.rgb(180, 171, 211)
    private val purple = Color.rgb(157, 92, 255)
    private lateinit var appGrid: GridLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = Color.rgb(8, 7, 17)
        window.navigationBarColor = Color.rgb(8, 7, 17)
        buildHome()
    }

    override fun onResume() {
        super.onResume()
        if (::appGrid.isInitialized) populateApps()
    }

    private fun rounded(color: Int, stroke: Int = 0): GradientDrawable =
        GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(color, Color.rgb(20, 17, 39))
        ).apply {
            cornerRadius = dp(20).toFloat()
            if (stroke != 0) setStroke(dp(1), stroke)
        }

    private fun buildHome() {
        val scroll = ScrollView(this).apply {
            setBackgroundColor(Color.rgb(8, 7, 17))
            isFillViewport = true
        }
        val page = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(dp(22), dp(24), dp(22), dp(20))
            background = GradientDrawable(
                GradientDrawable.Orientation.TL_BR,
                intArrayOf(Color.rgb(35, 22, 67), Color.rgb(8, 7, 17), Color.rgb(8, 19, 38))
            )
        }

        val brand = TextView(this).apply {
            text = "N O V A   O S"
            textSize = 19f
            letterSpacing = 0.18f
            typeface = Typeface.create("sans-serif", Typeface.BOLD)
            setTextColor(white)
        }
        page.addView(brand)

        val version = TextView(this).apply {
            text = "ANDROID LAUNCHER  •  CONCEPT 1.0"
            textSize = 10f
            letterSpacing = 0.12f
            setTextColor(muted)
            setPadding(0, dp(5), 0, dp(22))
        }
        page.addView(version)

        val time = TextView(this).apply {
            text = SimpleDateFormat("h:mm", Locale.getDefault()).format(Date())
            textSize = 58f
            typeface = Typeface.create("sans-serif-light", Typeface.NORMAL)
            setTextColor(white)
        }
        page.addView(time)
        val date = TextView(this).apply {
            text = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(Date())
            textSize = 13f
            setTextColor(muted)
            setPadding(0, 0, 0, dp(22))
        }
        page.addView(date)

        val ai = TextView(this).apply {
            text = "✧   NOVA AI"
            gravity = Gravity.CENTER
            textSize = 17f
            letterSpacing = 0.12f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(white)
            background = rounded(Color.rgb(50, 30, 91), Color.rgb(111, 79, 166))
            setPadding(dp(16), dp(22), dp(16), dp(22))
            setOnClickListener { showInfo("NOVA AI", "AI assistant integration will be added in a later version.") }
        }
        page.addView(ai, LinearLayout.LayoutParams(-1, dp(94)))

        val heading = TextView(this).apply {
            text = "YOUR APPS"
            textSize = 12f
            letterSpacing = 0.16f
            typeface = Typeface.DEFAULT_BOLD
            setTextColor(muted)
            setPadding(0, dp(25), 0, dp(13))
        }
        page.addView(heading)

        appGrid = GridLayout(this).apply { columnCount = 4 }
        page.addView(appGrid, LinearLayout.LayoutParams(-1, -2))
        val footer = TextView(this).apply {
            text = "NOVA • BLACK / PURPLE / NEON BLUE"
            gravity = Gravity.CENTER
            textSize = 10f
            letterSpacing = 0.10f
            setTextColor(Color.rgb(128, 115, 164))
            setPadding(0, dp(28), 0, dp(10))
        }
        page.addView(footer)
        scroll.addView(page)
        setContentView(scroll)
        populateApps()
    }

    private fun populateApps() {
        appGrid.removeAllViews()
        val intent = Intent(Intent.ACTION_MAIN, null).addCategory(Intent.CATEGORY_LAUNCHER)
        val apps = packageManager.queryIntentActivities(intent, 0)
            .filter { it.activityInfo.packageName != packageName }
            .sortedBy { it.loadLabel(packageManager).toString().lowercase(Locale.getDefault()) }

        for (info in apps) {
            val cell = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                setPadding(dp(3), dp(8), dp(3), dp(12))
                isClickable = true
                isFocusable = true
                setOnClickListener {
                    val launch = packageManager.getLaunchIntentForPackage(info.activityInfo.packageName)
                    if (launch != null) startActivity(launch)
                    else showInfo("NOVA", "This app could not be opened.")
                }
            }
            val icon = ImageView(this).apply {
                setImageDrawable(info.loadIcon(packageManager))
                scaleType = ImageView.ScaleType.FIT_CENTER
                background = rounded(Color.rgb(34, 27, 57), Color.rgb(78, 60, 115))
                setPadding(dp(9), dp(9), dp(9), dp(9))
            }
            cell.addView(icon, LinearLayout.LayoutParams(dp(54), dp(54)))
            val label = TextView(this).apply {
                text = info.loadLabel(packageManager)
                textSize = 10f
                maxLines = 1
                gravity = Gravity.CENTER
                setTextColor(white)
                setPadding(0, dp(6), 0, 0)
            }
            cell.addView(label, LinearLayout.LayoutParams(-1, -2))
            val params = GridLayout.LayoutParams().apply {
                width = 0
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
            }
            appGrid.addView(cell, params)
        }
    }

    private fun showInfo(title: String, message: String) {
        android.app.AlertDialog.Builder(this)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }

    private fun dp(value: Int): Int = (value * resources.displayMetrics.density).toInt()
}
