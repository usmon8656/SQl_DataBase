package com.example.sqldatabase

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.sqldatabase.databinding.ActivityMainBinding
import com.example.sqldatabase.databinding.ItemDialogBinding

class MainActivity : AppCompatActivity() , MyRvAdapter.RvAction {
    private lateinit var binding: ActivityMainBinding
    private lateinit var myRvAdapter: MyRvAdapter
    private lateinit var list:ArrayList<Contact>
    private lateinit var myDbHelper: MyDbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)





        myDbHelper=MyDbHelper(this)
        list= ArrayList()
        list.addAll(myDbHelper.getAllContacts())
        myRvAdapter= MyRvAdapter(list,this)
        binding.rv.adapter = myRvAdapter






        binding.btnAdd.setOnClickListener {
            val dialod = AlertDialog.Builder(this).create()

            val itemDialogBinding = ItemDialogBinding.inflate(layoutInflater)
            dialod.setView(itemDialogBinding.root)

            itemDialogBinding.btnSave.setOnClickListener {
                if (itemDialogBinding.edtName.text.isNotEmpty() && itemDialogBinding.edtNumber.text.isNotEmpty()){
                    val contact = Contact(name = itemDialogBinding.edtName.text.toString(), number = itemDialogBinding.edtNumber.text.toString())
                    list.add(contact)
                    myDbHelper.addContact(contact)
                    myRvAdapter.notifyDataSetChanged()
                    dialod.cancel()
                }else{
                    Toast.makeText(this, "Xammasin to`ldiring", Toast.LENGTH_SHORT).show()
                }

            }
            dialod.show()
        }






    }

    override fun rvEditClick(contact: Contact) {
        myDbHelper.deleteContact(contact)
        list.remove(contact)
        myRvAdapter.notifyDataSetChanged()
    }

    override fun rvDeleteClick(contact: Contact) {
        val dialog =AlertDialog.Builder(this).create()
        val dialogItem = ItemDialogBinding.inflate(layoutInflater)
        dialog.setView(dialogItem.root)
        dialogItem.edtName.setText(contact.name)
        dialogItem.edtNumber.setText(contact.number)

        dialogItem.btnSave.setOnClickListener {
            val contact2 = Contact(
                id = contact.id,
                name = dialogItem.edtName.text.toString(),
                number = dialogItem.edtNumber.text.toString()
            )
            myDbHelper.editContact(contact2)
            list.clear()
            list.addAll(myDbHelper.getAllContacts())
            myRvAdapter.notifyDataSetChanged()
            dialog.cancel()
        }

        dialog.show()
    }
}