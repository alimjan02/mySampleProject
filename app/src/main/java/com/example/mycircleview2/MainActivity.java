package com.example.mycircleview2;

import android.content.Context;
import android.os.Bundle;

import com.example.mycircleview2.gallery.GalleryViewPager;
import com.example.mycircleview2.gallery.ScalePageTransformer;
import com.example.mycircleview2.gallery.adapter.MyPageradapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ShimmerTextView centerText;
    private Shimmer shimmer;

    private ShimmerButton shimmerButton;
    private Shimmer buttonShimmer;

    private GalleryViewPager galleryViewPager;
    private SimpleAdapter galleryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        centerText = findViewById(R.id.center_text);
        shimmerButton = findViewById(R.id.shimmer_button);
        galleryViewPager = findViewById(R.id.gallery_view);
        galleryViewPager.setPageTransformer(true,new ScalePageTransformer());
        findViewById(R.id.gallery_root_view).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return galleryViewPager.dispatchTouchEvent(event);
            }
        });

        galleryAdapter = new SimpleAdapter(this);
        galleryViewPager.setAdapter(galleryAdapter);

        setSupportActionBar(toolbar);

        initGalleryData();
    }

    private void initGalleryData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.drawable.rotation);
        list.add(R.drawable.rotation);
        list.add(R.drawable.ic_launcher_foreground);
        list.add(R.drawable.ic_launcher_background);
        list.add(R.drawable.ic_launcher_foreground);
        list.add(R.drawable.rotation);
        galleryViewPager.setOffscreenPageLimit(Math.min(list.size(), 5));
        galleryAdapter.addAll(list);
        galleryViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        galleryAdapter.addOnSetPrimaryItemListener(new MyPageradapter.OnSetPrimaryItemListener() {
            @Override
            public void OnSetPrimaryItem(View view) {
                if (view instanceof ImageView) {
                    ImageView imageView = (ImageView) view;
                    imageView.getAnimation().cancel();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void toggleAnimation(View target) {
        if (shimmer != null && shimmer.isAnimating()) {
            shimmer.cancel();
        } else {
            shimmer = new Shimmer();
            shimmer.start(centerText);
        }
    }

    public void shimmerButtonAnimation(View target) {
        if (buttonShimmer != null && buttonShimmer.isAnimating()) {
            buttonShimmer.cancel();
        } else {
            buttonShimmer = new Shimmer();
            buttonShimmer.start(shimmerButton);
        }
    }

    public class SimpleAdapter extends MyPageradapter {

        private final List<Integer> mList;
        private final Context mContext;

        public SimpleAdapter(Context context) {
            mList = new ArrayList<>();
            mContext = context;
        }

        public void addAll(List<Integer> list) {
            mList.addAll(list);
            notifyDataSetChanged();
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup container) {
            ImageView imageView = null;
            if (convertView == null) {
                imageView = new ImageView(mContext);
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setTag(position);
            imageView.setImageResource(mList.get(position));
            imageView.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.rotation_anim));
            imageView.getAnimation().setInterpolator(new LinearInterpolator());
            imageView.getAnimation().start();
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((galleryViewPager.getCurrentItem() ) == position) {
                        Toast.makeText(mContext, "点击的位置是:::"+position, Toast.LENGTH_SHORT).show();
                    }

                }
            });
//            }

            return imageView;
        }

        @Override
        public int getCount() {
            return mList.size();
        }
    }
}
