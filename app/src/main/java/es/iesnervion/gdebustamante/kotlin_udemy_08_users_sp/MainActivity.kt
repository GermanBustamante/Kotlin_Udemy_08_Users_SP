package es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import es.iesnervion.gdebustamante.kotlin_udemy_08_users_sp.databinding.ActivityMainBinding

//TODO *******************************************************************
//Esta app se centra en el uso de datos, actualizar, borrar, eliminar
//Listar una serie de usuarios (RecyclerViews y Adapters)
//Diseño de un listado con MDC
//Almacenamiento permanente con Shared Preferences
//Android Dialog con vistas personalizadas
//Interfaces (práctica para alimentar, configurar, comunicar un listado)
//TODO *******************************************************************

class MainActivity : AppCompatActivity() {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Shared Preferences. funciona por pares clave-valor
        val preferences =
            getPreferences(MODE_PRIVATE)//getPreferences para solo nuestra app, getSharedPreferences para compartir con otras apps
        //Almacenar un dato
        val isFirstTime = preferences.getBoolean(
            getString(R.string.sp_first_time),
            true
        )//Todos sus getXXXX necesitan al final un valor por defecto por si esta vacio
        var username =
            preferences.getString(getString(R.string.sp_username), "NA")//NA -> No Available
        Log.i("SP_isFirstTime", "$isFirstTime")
        Log.i("SP_username", "$username")
        //Insertar un dato, vemos que la primera vez que lo usamos se guarda en
        if (isFirstTime) {
            //Dialogos con MDC, crearemos uno por defecto y le daremos el aspecto que queremos de un xml
            //Inflamos el dialog
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_tittle)
                .setView(dialogView)//Le damos la vista
                .setCancelable(false)//El dialogo no desaparece por ninguna forma a no ser que le demos a un botón
                .setPositiveButton(R.string.dialog_confirm) { dialogInterface, witch ->
//                    preferences.edit().putBoolean(getString(R.string.sp_first_time), false)
//                        .commit()//Cuidado, commit
                    //bloquea la interfaz de usuarion ya que es una operacion sincrona

                    //Recogemos el string del ed
                    username =
                        dialogView.findViewById<TextInputEditText>(R.id.etUsername).text.toString()

                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                        apply()//apply() cambia el objeto SharedPreferences en la memoria de inmediato, pero escribe
                        // las actualizaciones en el disco de forma asíncrona
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT).show()
                }.setNeutralButton(R.string.dialog_neutral, null)//Boton para ir como invitado
                .show()
        } else {
            username = preferences.getString(
                getString(R.string.sp_username),
                getString(R.string.hint_username)
            )
            Toast.makeText(this, "Bienvenido $username", Toast.LENGTH_SHORT).show()
        }

        //Alternativa al diseño que tenemos actualmente, usando tarjetas, usando item_user-alt.xml
        //que tiene divisor por default y el evento de click y lo hace más potente

        //Adapter y ViewHolder
        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recVw.apply {
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val alain = User(
            1,
            "Alain",
            "Nicolás",
            "https://frogames.es/wp-content/uploads/2020/09/alain-1.jpg"
        )
        val samanta = User(
            2,
            "Samanta",
            "Meza",
            "https://upload.wikimedia.org/wikipedia/commons/b/b2/Samanta_villar.jpg"
        )
        val javier = User(
            3,
            "Javier",
            "Gómez",
            "https://live.staticflickr.com/974/42098804942_b9ce35b1c8_b.jpg"
        )
        val emma = User(
            4,
            "Emma",
            "Cruz",
            "https://upload.wikimedia.org/wikipedia/commons/d/d9/Emma_Wortelboer_%282018%29.jpg"
        )

        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)
        users.add(alain)
        users.add(samanta)
        users.add(javier)
        users.add(emma)

        return users
    }

    override fun onClick(
        user: User,
        position: Int
    ) {//TODO INTENTAR ENTENDER COMO FUNCIONA LOS LISTENER EN ADAPTERS
        Toast.makeText(this, "$position ${user.getFullName()}", Toast.LENGTH_SHORT).show()
    }
}