package studios.gomez.com.tomatlan.traely.Adaptadores;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.List;

import studios.gomez.com.tomatlan.traely.Objetos.Promocion;
import studios.gomez.com.tomatlan.traely.R;


/**
 * Created by gomez on 5/11/17.
 */

public class AdaptadorPromociones extends PagerAdapter {

    List<Promocion> listaPromociones;
    Context context;

    public AdaptadorPromociones(List<Promocion> listaPromociones, Context context) {
        this.listaPromociones = listaPromociones;
        this.context = context;
    }


    @Override
    public int getCount() {
        return listaPromociones.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = layoutInflater.inflate(R.layout.viewpager_promociones,null);

        ImageView imagen = view.findViewById(R.id.imagen_promocion);

        Glide.with(context)
                .load(listaPromociones.get(position).getImagen())
                .fitCenter()
                .centerCrop()
                .into(imagen);

        ViewPager vp = (ViewPager)container;
        vp.addView(view,0);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){

        ViewPager vp = (ViewPager)container;
        View view = (View)object;
        vp.removeView(view);

    }
}
