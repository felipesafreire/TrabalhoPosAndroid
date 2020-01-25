package br.fib.bar.adapter

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import br.fib.bar.db.Bar
import br.fib.bar.R
import java.io.IOException
import java.io.InputStream

class BarListaAdapter(context: Context, lista: ArrayList<Bar>, assetManager: AssetManager) :
    BaseAdapter() {

    private var lista: ArrayList<Bar>
    private var inflator: LayoutInflater
    private var assetManager: AssetManager

    init {
        this.lista = lista
        this.inflator = LayoutInflater.from(context)
        this.assetManager = assetManager
    }

    override fun getCount(): Int {
        return this.lista.size
    }

    override fun getItem(position: Int): Any? {
        return this.lista.get(position)
    }

    override fun getItemId(position: Int): Long {
        return this.lista.get(position).id;
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {

        val bar = this.lista.get(position)
        val view = this.inflator.inflate(R.layout.linha_lista_bar, parent, false)

        (view.findViewById<TextView>(R.id.nome)).text = bar.nome
        (view.findViewById<TextView>(R.id.horarioBar)).text = bar.horarios

        val bitmapImage = this.getBitmapFromAsset(bar.imagem.toString())
        var imageView = view.findViewById<ImageView>(R.id.barImagem)
        imageView.setImageBitmap(bitmapImage)

        return view

    }

    private fun getBitmapFromAsset(imagem: String): Bitmap {
        val assetManager = this.assetManager
        var istr: InputStream
        try {
            istr = assetManager.open("images/$imagem")
        } catch (e: IOException) {
            istr = assetManager.open("images/semfoto.jpg")
        }
        return BitmapFactory.decodeStream(istr)
    }


}
