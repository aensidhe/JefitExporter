package ru.AenSidhe.jefit.exporter.xlsx;

import android.os.*;
import android.view.*;
import android.app.*;
import org.joda.time.LocalDate;

import java.util.*;
import android.widget.*;
import java.io.*;

public class JeFitExport extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		Utils.SetPackageName(getPackageName());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jefit_export);
	}
	
	public void exportButtonClick(View view)
	{
		LocalDate startDate = getStartDateEditor().getDate();
		LocalDate endDate = getEndDateEditor().getDate();
		Boolean isPro = getProEditor().isChecked();
		CharSequence message;

		try
		{
			Iterator<Set> data = new Exporter(startDate, endDate, isPro)
				.readData()
				.getData()
				.iterator();
				
			DataFormatter f = new ExcelFormatter(this);

			try
			{
				message = f.FormatSets(data);
			}
			catch (IOException e)
			{
				message = e.getMessage();
			}	
		}
		catch (FileNotFoundException e)
		{
			message = e.getMessage();
		}
		catch (IOException e)
		{
			message = e.getMessage();
		}

		Toast.makeText(this, message, Toast.LENGTH_SHORT);
	}
	
	private DateTimeFragment getStartDateEditor()
	{
		return (DateTimeFragment) getFragmentManager().findFragmentById(R.id.jefit_export_startDate);
	}
	
	private DateTimeFragment getEndDateEditor()
	{
		return (DateTimeFragment) getFragmentManager().findFragmentById(R.id.jefit_export_endDate);
	}
	
	private CheckBox getProEditor()
	{
		return (CheckBox) findViewById(R.id.jefit_export_pro);
	}
}
