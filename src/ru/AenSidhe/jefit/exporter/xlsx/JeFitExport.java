package ru.AenSidhe.jefit.exporter.xlsx;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.app.*;
import org.joda.time.LocalDate;

import java.io.FileNotFoundException;
import java.util.*;

public class JeFitExport extends Activity
{
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jefit_export);
	}
	
	public void exportButtonClick(View view)
	{
		LocalDate startDate = getStartDateEditor().getDate();
		LocalDate endDate = getEndDateEditor().getDate();
		CharSequence message;

		try
		{
			Iterator<ExerciseData> data = new Exporter(startDate, endDate)
				.readData()
				.getData()
				.iterator();
				
			StringBuilder sb = new StringBuilder();
			
			while(data.hasNext())
			{
				ExerciseData next = data.next();
				Iterator<String> logIterator = next.getLogIterator();
				while (logIterator.hasNext())
					sb.append(String.format("%s - %s", next.getName(), logIterator.next())).append("\n");
			}
			
			message = sb.toString();
		}
		catch (FileNotFoundException e)
		{
			message = e.getMessage();
		}

		new AlertDialog.Builder(this)
			.setMessage(message)
			.create()
			.show();
	}
	
	private DateTimeFragment getStartDateEditor()
	{
		return (DateTimeFragment) getFragmentManager().findFragmentById(R.id.jefit_export_startDate);
	}
	
	private DateTimeFragment getEndDateEditor()
	{
		return (DateTimeFragment) getFragmentManager().findFragmentById(R.id.jefit_export_endDate);
	}
}
