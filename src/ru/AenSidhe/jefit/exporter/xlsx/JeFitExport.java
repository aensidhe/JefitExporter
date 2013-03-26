package ru.AenSidhe.jefit.exporter.xlsx;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.app.*;
import org.joda.time.LocalDate;

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
		new AlertDialog.Builder(this)
			.setMessage(
				(startDate != null ? startDate.toString() : "<null>") +
				"\n" + 
				(endDate != null ? endDate.toString() : "<null>")
			)
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
