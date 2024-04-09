package edu.ppsm.lab6

import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val dbMaterial = CDBMaterial(this)
    private var recyclerView:RecyclerView?=null
    private var adapter: CRecyclerViewAdapter?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbMaterial.open()
        adapter= CRecyclerViewAdapter(this, dbMaterial.materials)
        recyclerView=findViewById(R.id.viewList)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager=LinearLayoutManager(this)
        recyclerView?.adapter=adapter
        recyclerView?.adapter!!.notifyItemRangeInserted(0,adapter!!.itemCount)

        findViewById<View>(R.id.buttonAdd).setOnClickListener{v:View?->dbMaterial.addMaterial(
            (findViewById<EditText>(R.id.editType)).text.toString(),
            (findViewById<EditText>(R.id.editStandard)).text.toString() )
            adapter!!.swapCursor(dbMaterial.materials)
            recyclerView?.adapter!!.notifyItemInserted(adapter!!.itemCount)
            //recyclerView?.scheduleLayoutAnimation()
        }
    }
}