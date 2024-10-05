package com.example.happyplaces.activities

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.happyplaces.R
import com.example.happyplaces.databinding.ActivityAddHappyPlaceBinding
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddHappyPlaceActivity : AppCompatActivity(),View.OnClickListener {
    private var binding: ActivityAddHappyPlaceBinding? = null
    private var calender = Calendar.getInstance()
    private lateinit var dateSetListener: DatePickerDialog.OnDateSetListener
    private var saveImageToInternalStorage: Uri? = null
    private var mLatitude: Double = 0.0
    private var mLongitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddHappyPlaceBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        setSupportActionBar(binding!!.toolbarAddPlace)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        binding!!.toolbarAddPlace.setNavigationOnClickListener{
            onBackPressed()
        }
        dateSetListener=DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            calender.set(Calendar.YEAR,year)
            calender.set(Calendar.MONTH,month)
            calender.set(Calendar.DAY_OF_MONTH,dayOfMonth)
            updateInView()
        }
        binding!!.etDate.setOnClickListener(this)
        binding!!.tvAddImage.setOnClickListener(this)
        binding!!.btnSave.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){
            R.id.et_date ->{
                DatePickerDialog(this@AddHappyPlaceActivity,dateSetListener,
                    calender.get(Calendar.YEAR),
                    calender.get(Calendar.MONTH),
                    calender.get(Calendar.DAY_OF_MONTH)).show()
            }

        }
    }
    private fun updateInView(){
        val myFormat="dd.MM.yyyy"
        val sdf= SimpleDateFormat(myFormat, Locale.getDefault())
        binding!!.etDate.setText(sdf.format(calender.time).toString())
    }

    private fun takePhotoFromCamera(){
        if(checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED ){

        }
    }

    private fun showRationalDialogForPermissions() {
        AlertDialog.Builder(this)
            .setMessage("It Looks like you have turned off permissions required for this feature. It can be enabled under Application Settings")
            .setPositiveButton("GO TO SETTINGS"
            ) { _, _ ->
                try {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    val uri = Uri.fromParts("package", packageName, null)
                    intent.data = uri
                    startActivity(intent)
                } catch (e: ActivityNotFoundException) {
                    e.printStackTrace()
                }
            }
            .setNegativeButton("Cancel") { dialog,
                                           _ ->
                dialog.dismiss()
            }.show()
    }
}