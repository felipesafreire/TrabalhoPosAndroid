import android.content.Context
import android.database.sqlite.SQLiteDatabase
import br.fib.bar.db.ConstantDB
import org.jetbrains.anko.db.ManagedSQLiteOpenHelper
import org.jetbrains.anko.db.*

class BancoDadosHelper(context: Context) :
    ManagedSQLiteOpenHelper(
        ctx = context,
        name = ConstantDB.DB_NAME, version = 1
    ) {

    private val scriptSQLCreate = arrayOf(
        "INSERT INTO ${ConstantDB.DB_TABLE_BARES} VALUES(1, 'Bar do Português', 'Ter. a Sab. - 20:00 as 02:00hrs', 'bar1.jpg');",
        "INSERT INTO ${ConstantDB.DB_TABLE_BARES} VALUES(2, 'Butequim - Sampaio Vidal', 'Ter. a Sab. - 18:00 as 04:00hrs', 'bar2.jpg');",
        "INSERT INTO ${ConstantDB.DB_TABLE_BARES} VALUES(3, 'Buteco', 'Ter. a Sab. - 12:00 as 06:00hrs', 'bar3.jpg');",
        "INSERT INTO ${ConstantDB.DB_TABLE_BARES} VALUES(4, 'Bar do Amorim', 'Ter. a Sab. - 10:00 as 18:00hrs', null);"
    )

    //singleton da classe
    companion object {
        private var instance: BancoDadosHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): BancoDadosHelper {
            if (instance == null) {
                instance = BancoDadosHelper(ctx.getApplicationContext())
            }
            return instance!!
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação de tabelas
        db.createTable(
            ConstantDB.DB_TABLE_BARES, true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            "nome" to TEXT,
            "horarios" to TEXT,
            "imagem" to TEXT
        )

        scriptSQLCreate.forEach { sql ->
            db.execSQL(sql)
        }

    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable("${ConstantDB.DB_TABLE_BARES}", true)
        onCreate(db)
    }

}

val Context.database: BancoDadosHelper get() = BancoDadosHelper.getInstance(getApplicationContext())
