package ru.AenSidhe.jefit.exporter.xlsx;

import java.io.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.hssf.usermodel.*;
import org.joda.time.*;
import android.content.*;
import android.os.*;
import android.widget.*;

public class ExcelFormatter extends DataFormatter
{
	public ExcelFormatter(Context context)
	{
		super(context);
	}

	protected String GetFileName()
	{
		return String.format("jefit_%s.xls", _now.toString("yyyyMMdd_HHmmss"));
	}
	
	protected void Init()
	{
		_workbook = new HSSFWorkbook();
	}
	
	protected void Save(FileOutputStream stream) throws IOException
	{
		_workbook.write(stream);
	}
	
	protected void WriteExerciseHeader(String name)
	{
		_currentSheet = _workbook.createSheet(name);
		_currentRowIndex = 0;
		BuildHeader();
	}

	protected void WriteLogEntry(LocalDate date, ExerciseData entry)
	{
		ArrayList<String> row = new ArrayList<String>();
		row.add(date.toString());
		row.add(Double.toString(entry.getWeight()));
		row.add(Integer.toString(entry.getReps()));

		BuildRow(row);
	}
	
	private void BuildHeader()
	{
		ArrayList<String> header = new ArrayList<String>();
		header.add("Date");
		header.add("Weight");
		header.add("Reps");
		BuildRow(header);
	}

	private void BuildRow(List<String> values)
	{
		if (values == null)
			return;

		Row row = _currentSheet.createRow(_currentRowIndex++);
		for (Integer i = 0; i < values.size(); i++)
			row.createCell(i).setCellValue(values.get(i));
	}
	
	private int _currentRowIndex;
	
	private Sheet _currentSheet;
	
	private Workbook _workbook;
}
