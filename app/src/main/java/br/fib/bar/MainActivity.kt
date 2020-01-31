package br.fib.bar

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import br.aula.agenda.db.BarRepository
import br.fib.bar.adapter.BarListaAdapter
import br.fib.bar.db.Bar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myToolbar = toolbar
        myToolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(myToolbar)

    }

    override fun onResume() {

        super.onResume()
        val listaBares = BarRepository(this).findAll()
        val adapter = BarListaAdapter(this, listaBares, applicationContext.assets)

        var lista = findViewById(R.id.listaBares) as ListView
        lista?.adapter = adapter
        adapter.notifyDataSetChanged()

        adapter.notifyDataSetChanged()

        lista.setOnItemClickListener { _, _, position, id ->
            val intent = Intent(this, CadastroBarActivity::class.java)
            intent.putExtra("bar", listaBares?.get(position))
            startActivity(intent)
        }

        lista.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapter, view, posicao, id ->
            var barSelecionadosId = listaBares?.get(posicao).id
            BarRepository(this).delete(barSelecionadosId.toInt())
            onResume()
            false
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.novo -> {
                val intent = Intent(this, CadastroBarActivity::class.java)
                startActivity(intent)
                return false
            }
            else -> return super.onOptionsItemSelected(item)
        }

    }

}
