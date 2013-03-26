package ru.AenSidhe.jefit.exporter.xlsx;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.CancellationSignal;
import org.joda.time.LocalDate;

import java.io.FileNotFoundException;
import java.util.*;

public class Exporter
{
	public Exporter(LocalDate start, LocalDate end, Boolean usePro)
	{
		_start = start;
		_end = end;
		_usePro = usePro;
	}

	public List<ExerciseData> getData()
	{
		return _data;
	}

	public Exporter readData() throws FileNotFoundException
	{
		SQLiteDatabase db = null;
		_data = new ArrayList<ExerciseData>();
		try
		{
			db = SQLiteDatabase.openDatabase(getDatabasePath(), null, SQLiteDatabase.OPEN_READONLY);
			if (db == null)
				throw new FileNotFoundException("database wasn't found");

			Cursor cursor = db.rawQuery("select ename, logs from exerciseLogs order by mydate", null);
			while (cursor.moveToNext())
			{
				_data.add(new ExerciseData(cursor.getString(0), cursor.getString(1), new LocalDate()));
				if (_data.size() == 2)
					break;
			}
			cursor.close();
		}
		finally
		{
			if (db != null)
				db.close();
		}

		return this;
	}

	private String getDatabasePath()
	{
		final String nonPro = "/sdcard/jefit/jefit.bak";
		final String pro = "/sdcard/jefit/jefit_pro.bak";
		return _usePro ? pro : nonPro;
	}
	
	private final LocalDate _start;
	private final LocalDate _end;
	private List<ExerciseData> _data;
	private final Boolean _usePro;
}
