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

import studios.gomez.com.tomatlan.traely.Objetos.FotoVPMenu;
import studios.gomez.com.tomatlan.traely.R;

/**
 * Created by gomez on 15/11/17.
 */

public class AdaptadorFotosDelNegocio extends PagerAdapter {

    List<FotoVPMenu> listaImagenes;
    Context context;

    public AdaptadorFotosDelNegocio(List<FotoVPMenu> listaImagenes, Context context) {
        this.listaImagenes = listaImagenes;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listaImagenes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = layoutInflater.inflate(R.layout.viewpager_menu,null);

        ImageView imagen = v.findViewById(R.id.imagen_viewpager_menu);

        Glide.with(context)
                .load(listaImagenes.get(position).getImagen())
                .fitCenter()
                .centerCrop()
                .into(imagen);

        ViewPager vp = (ViewPager)container;
        vp.addView(v,0);


        return v;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ViewPager vp = (ViewPager)container;
        View v = (View)object;
        vp.removeView(v);

    }
}
