package weather.android.com.utils


import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import weather.android.com.R


class ManageFragment {

    companion object {

        /**
         * Replace the fragment using fragmentManager
         *
         * @param frag           : fl_fragment_container will be replaced by frag
         * @param addToBackStack : if empty no backstack else addToBackStack
         */
        fun replaceFrag(activity: FragmentActivity, frag: Fragment, addToBackStack: String) {
            try {
                if (TextUtils.isEmpty(addToBackStack)) {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container, frag)
                        .commit()
                } else {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .replace(R.id.fragment_container, frag)
                        .addToBackStack(addToBackStack)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("Activity >> ", "Replace Fragment $e")
            }

        }

        /**
         * Add the fragment using fragmentManager
         *
         * @param frag           : fl_fragment_container will be replaced by frag
         * @param addToBackStack : if empty no backstack else addToBackStack
         */
        fun addFrag(activity: FragmentActivity, frag: Fragment, addToBackStack: String) {
            try {
                if (TextUtils.isEmpty(addToBackStack)) {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(R.id.fragment_container, frag)
                        .commit()
                } else {
                    activity.supportFragmentManager.beginTransaction()
                        .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                        .add(R.id.fragment_container, frag)
                        .addToBackStack(addToBackStack)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("Activity >> ", "Add Fragment $e")
            }

        }


        /**
         * Replace the fragment using fragmentManager
         *
         * @param frag           : fl_fragment_container will be replaced by frag
         * @param addToBackStack : if empty no backstack else addToBackStack
         */
        fun replaceFragWithAnim(activity: FragmentActivity, frag: Fragment, addToBackStack: String) {
            try {
                if (TextUtils.isEmpty(addToBackStack)) {
                    activity.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, 0, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, frag)
                        .commit()
                } else {
                    activity.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, 0, R.anim.exit_to_right)
                        .replace(R.id.fragment_container, frag)
                        .addToBackStack(addToBackStack)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("Activity >> ", "Replace Fragment with animation $e")
            }

        }


        /**
         * Add the fragment using fragmentManager
         *
         * @param frag           : fl_fragment_container will be replaced by frag
         * @param addToBackStack : if empty no backstack else addToBackStack
         */
        fun addFragWithAnim(activity: FragmentActivity, frag: Fragment, addToBackStack: String) {
            try {
                if (TextUtils.isEmpty(addToBackStack)) {
                    activity.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, 0, R.anim.exit_to_right)
                        .add(R.id.fragment_container, frag)
                        .commit()
                } else {
                    activity.supportFragmentManager.beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, 0, R.anim.exit_to_right)
                        .add(R.id.fragment_container, frag)
                        .addToBackStack(addToBackStack)
                        .commit()
                }
            } catch (e: Exception) {
                Log.e("Activity >> ", "Add Fragment with animation $e")
            }

        }

        /**
         * To remove all fragments from stack
         */
        fun emptyTheStack(activity: FragmentActivity) {
            try {
                val count = activity.supportFragmentManager.backStackEntryCount
                for (i in 0 until count) {
                    activity.supportFragmentManager.popBackStack()
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

        /**
         * To remove a fragments from stack
         */
        fun removeFragment(activity: FragmentActivity) {
            activity.supportFragmentManager.popBackStack()
        }

    }
}
