package com.a2a.app

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import com.a2a.app.databinding.ActivityMainBinding
import com.a2a.app.utils.AppUtils
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.onesignal.OneSignal
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    // var cartCounter: String? = ""
    private lateinit var viewBinding: ActivityMainBinding

    // index to identify current nav menu item
    private var navItemIndex = 0

    // tags used to attach the fragments
    private val TAG_HOME = "home"
    private val TAG_ORDERS = "orders"
    private val TAG_PROFILE = "profile"
    private val TAG_CONTACT_US = "contact"
    private val TAG_RATE = "rate"
    private var CURRENT_TAG = TAG_HOME
    private var actionBarDrawerToggle: ActionBarDrawerToggle? = null

    // toolbar titles respected to selected nav menu item
    private var activityTitles: Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        //pushNotification()

        activityTitles = resources.getStringArray(R.array.nav_item_activity_titles)
        setUpNavigationView()

        viewBinding.navigation.menu.getItem(0).isChecked = false;

        viewBinding.navigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.deals -> {
                    unSelectNavMenu()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_dealsFragment)
                }

                R.id.city -> {
                    unSelectNavMenu()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_cityFragment)
                }
                R.id.serviceType -> {
                    unSelectNavMenu()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_serviceTypeFragment)
                }
                R.id.category -> {
                    unSelectNavMenu()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_categoryFragment)
                }
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun selectNavMenu() {
        viewBinding.navView.menu.getItem(navItemIndex).isChecked = true
    }

    fun selectHomeNavMenu() {
        viewBinding.navView.menu.getItem(0).isChecked = true
    }

    private fun unSelectNavMenu() {
        viewBinding.navView.menu.getItem(navItemIndex).isChecked = false
    }

    private fun closeDrawer() {
        viewBinding.drawerLayout.closeDrawer(Gravity.LEFT)
    }

    private fun setUpNavigationView() {
        setSupportActionBar(viewBinding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        supportActionBar?.title = getString(R.string.app_name)
        title = getString(R.string.app_name)

        actionBarDrawerToggle =
            ActionBarDrawerToggle(
                this,
                viewBinding.drawerLayout,
                viewBinding.toolbar,
                R.string.openDrawer,
                R.string.closeDrawer
            )

        //Setting the actionbarToggle to drawer layout
        actionBarDrawerToggle!!.drawerArrowDrawable.color = resources.getColor(R.color.white);
        viewBinding.drawerLayout.addDrawerListener(actionBarDrawerToggle!!)
        actionBarDrawerToggle!!.syncState()

        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        viewBinding.navView.setNavigationItemSelectedListener { menuItem ->
            // This method will trigger on item Click of navigation menu
            //Check to see which item was being clicked and perform appropriate action
            // Log.e("navigation drawer", menuItem.title.toString())
            when (menuItem.itemId) {
                //Replacing the main content with ContentFragment Which is our Inbox View;
                R.id.nav_home -> {
                    showToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_homeFragment)
                }
                R.id.nav_orders -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_orderFragment)
                }
                R.id.nav_profile -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_profileFragment)
                }
                R.id.nav_share -> {
                    val sharingIntent = Intent(Intent.ACTION_SEND)
                    sharingIntent.type = "text/plain"
                    val shareBody = buildString {
                        appendLine("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                    }
                    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
                    sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody)
                    startActivity(
                        Intent.createChooser(
                            sharingIntent,
                            getString(R.string.share_via)
                        )
                    )
                }
                R.id.bulk_order -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_bulkOrderFragment)
                }
                R.id.nav_rate -> {
                    val uri = Uri.parse("market://details?id=" + applicationContext.packageName)
                    val goToMarket = Intent(Intent.ACTION_VIEW, uri)
                    // To count with Play market back stack, After pressing back button,
                    // to taken back to our application, we need to add following flags to intent.
                    goToMarket.addFlags(
                        Intent.FLAG_ACTIVITY_NO_HISTORY or
                                Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                                Intent.FLAG_ACTIVITY_MULTIPLE_TASK
                    )
                    try {
                        startActivity(goToMarket)
                    } catch (e: ActivityNotFoundException) {
                        startActivity(
                            Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://play.google.com/store/apps/details?id=" + applicationContext.packageName)
                            )
                        )
                    }
                }
                R.id.nav_contact_us -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_contactUsFragment)
                }
                R.id.nav_logout -> {
                    AppUtils(this).logOut()
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_onBoardingFragment)
                }

                R.id.member_ship -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_memberShipFragment)
                }

                R.id.my_plan -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_myPlanFragment)
                }

                R.id.wallet -> {
                    hideToolbarAndBottomNavigation()
                    val navHostFragment =
                        supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(R.id.action_global_walletFragment)
                }

                else -> navItemIndex = 0
            }
            closeDrawer()
            //Checking if the item is in checked state or not, if not make it in checked state
            menuItem.isChecked = !menuItem.isChecked
            menuItem.isChecked = true

            true
        }
    }

    fun hideToolbarAndBottomNavigation() {
        with(viewBinding) {
            toolbar.visibility = View.GONE
            navigation.visibility = View.GONE
        }
    }

    fun showToolbarAndBottomNavigation() {
        with(viewBinding) {
            toolbar.visibility = View.VISIBLE
            navigation.visibility = View.VISIBLE
        }
    }

    fun setNavHeader() {
        //set header
        val headerTitle = viewBinding.navView.getHeaderView(0).findViewById<TextView>(R.id.name)
        headerTitle.text = AppUtils(this).getUser()!!.email
    }


    private fun pushNotification() {
        try {

            FirebaseMessaging.getInstance().subscribeToTopic("install")

            FirebaseMessaging.getInstance().token.addOnSuccessListener { token: String ->
                if (!TextUtils.isEmpty(token)) {
                    Log.e(
                        "Token",
                        "retrieve token successful : $token"
                    )
                } else {
                    Log.e(
                        "Token",
                        "token should not be null..."
                    )
                }
            }.addOnFailureListener { }.addOnCanceledListener {}
                .addOnCompleteListener { task: Task<String> ->
                    try {
                        Log.e(
                            "Token",
                            "This is the token : " + task.result
                        )
                    } catch (exception: Exception) {

                    }
                }
        } catch (exception: IOException) {
            Log.e("firebase", exception.toString())
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.book -> {
                hideToolbarAndBottomNavigation()
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.action_global_bookFragment)
            }
            R.id.wallet -> {
                hideToolbarAndBottomNavigation()
                val navHostFragment =
                    supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_home) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.action_global_walletFragment)
            }
        }
        return true
    }

    override fun onBackPressed() {
        if (viewBinding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            viewBinding.drawerLayout.closeDrawers()
            return
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (navItemIndex != 0) {
            navItemIndex = 0
            CURRENT_TAG = TAG_HOME
            return
        }

        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        navItemIndex = 0
        invalidateOptionsMenu()
        selectNavMenu()
    }

    override fun onDestroy() {
        super.onDestroy()
        AppUtils(this).clearHomePage()
        AppUtils(this).clearSettings()

        Log.e(
            "main",
            "Home : ${
                AppUtils(this).getHome().toString()
            }\nSettings : ${AppUtils(this).clearSettings()}"
        )
    }

}
