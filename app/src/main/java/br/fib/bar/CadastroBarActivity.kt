package br.fib.bar

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.aula.agenda.db.BarRepository
import br.fib.bar.db.Bar
import kotlinx.android.synthetic.main.activity_cadastro_bar.*

class CadastroBarActivity : AppCompatActivity() {

    var bar: Bar? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_bar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (intent?.getSerializableExtra("bar") != null) {

            bar = intent?.getSerializableExtra("bar") as Bar
            txtNome?.setText(bar?.nome)
            txtHorarios?.setText(bar?.horarios)

        }else{
            bar = Bar()
        }

        btnCadastro.setOnClickListener {

            bar?.nome = txtNome?.text.toString()
            bar?.horarios = txtHorarios?.text.toString()


            if(bar?.id?.toInt() == 0){
                BarRepository(this).create(bar!!)
            }else{
                BarRepository(this).update(bar!!)
            }

            finish()
        }


    }

}
