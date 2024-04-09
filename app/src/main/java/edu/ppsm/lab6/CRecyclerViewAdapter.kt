package edu.ppsm.lab6

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.ppsm.lab6.CRecyclerViewAdapter.CViewHolder



class CRecyclerViewAdapter(context: Context?, private var cData: Cursor?) :
    RecyclerView.Adapter<CViewHolder>() {

    interface IItemClickListener {
        fun onItemClick(view: View?, position: Int)
    }

    private var mClickListener: IItemClickListener? = null

    inner class CViewHolder internal constructor(itemView: View) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        var textId: TextView
        var textTyp: TextView
        var textNorma: TextView

        init {
            textId = itemView.findViewById(R.id.tvID)
            textTyp = itemView.findViewById(R.id.tvType)
            textNorma = itemView.findViewById(R.id.tvStandard)
            itemView.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            mClickListener?.onItemClick(view, adapterPosition)
        }
    }

    fun setClickListener(itemClickListener: IItemClickListener?) {
        mClickListener = itemClickListener
    }

    private val mInflater: LayoutInflater

    init {
        mInflater = LayoutInflater.from(context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CViewHolder {
        val view = mInflater.inflate(R.layout.list_item, parent, false)
        return CViewHolder(view)
    }

    override fun onBindViewHolder(holder: CViewHolder, position: Int) {
        var i: Int
        if (cData!!.moveToPosition(position)) {
            i = cData!!.getColumnIndex(TYPE_ID)
            holder.textId.text = cData!!.getInt(i).toString()
            i = cData!!.getColumnIndex(TYPE_TYPENAME)
            holder.textTyp.text = cData!!.getString(i)
            i = cData!!.getColumnIndex(TYPE_STANDARD)
            holder.textNorma.text = cData!!.getString(i)
        }
    }

    override fun getItemCount(): Int {
        return cData!!.count
    }

    fun getItem(id: Int): HashMap<String?, String?>? {
        var item: HashMap<String?, String?>? = null
        var i: Int
        if (cData!!.moveToPosition(id)) {
            item = HashMap()
            i = cData!!.getColumnIndex(TYPE_ID)
            item["_id"] = cData!!.getInt(i).toString()
            i = cData!!.getColumnIndex(TYPE_TYPENAME)
            item["typ"] = cData!!.getString(i)
            i = cData!!.getColumnIndex(TYPE_STANDARD)
            item["norma"] = cData!!.getString(i)
        }
        return item
    }

    fun swapCursor(newCursor: Cursor?) {
        if (cData != null) cData!!.close()
        cData = newCursor
        if (cData != null) notifyItemRangeChanged(0, itemCount)
    }
}
