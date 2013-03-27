package ru.AenSidhe.jefit.exporter.xlsx;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.app.*;
import org.joda.time.LocalDate;

import java.io.FileNotFoundException;
import java.util.*;
import android.widget.*;

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
		Boolean isPro = getProEditor().isChecked();
		CharSequence message;

		try
		{
			Iterator<Set> data = new Exporter(startDate, endDate, isPro)
				.readData()
				.getData()
				.iterator();
				
			StringBuilder sb = new StringBuilder();
			
			while(data.hasNext())
			{
				Set next = data.next();
				Iterator<ExerciseData> logIterator = next.getExercises();
				while (logIterator.hasNext())
				{
					final ExerciseData exerciseData = logIterator.next();
					sb.append(String.format("%s - %.1f - %d", next.getName(), exerciseData.getWeight(), exerciseData.getReps())).append("\n");
				}
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
	
	private CheckBox getProEditor()
	{
		return (CheckBox) findViewById(R.id.jefit_export_pro);
	}
}
