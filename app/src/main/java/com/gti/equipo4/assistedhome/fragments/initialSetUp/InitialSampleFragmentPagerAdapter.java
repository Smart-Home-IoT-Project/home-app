package com.gti.equipo4.assistedhome.fragments.initialSetUp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

import com.gti.equipo4.assistedhome.R;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class InitialSampleFragmentPagerAdapter extends FragmentStatePagerAdapter {
    final int PAGE_COUNT = 3;
    private int[] tabIcons = {
            R.drawable.ic_medic,
            R.drawable.ic_calendar,
            R.drawable.ic_appintro_arrow_back_white
    };
    private String[] tabTitles = new String[] { "1", "2", "3" };
    private Context context;

    public InitialSampleFragmentPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.context = context;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                InitialTabFragment1 tab1 = new InitialTabFragment1();
                return tab1;
            case 1:
                InitialTabFragment2 tab2 = new InitialTabFragment2();
                return tab2;
            case 2:
                InitialTabFragment3 tab3 = new InitialTabFragment3();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        // return tabTitles[position];
        Drawable image = context.getResources().getDrawable(tabIcons[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;
    }
}