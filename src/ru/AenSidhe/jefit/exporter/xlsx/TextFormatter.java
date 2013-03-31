package ru.AenSidhe.jefit.exporter.xlsx;
import android.content.*;
import java.io.*;
import org.joda.time.*;

public class TextFormatter extends DataFormatter
{
	public TextFormatter(Context context)
	{
		super(context);
	}

	protected String GetFileName()
	{
		return String.format("jefit_%s.txt", _now.toString("yyyyMMdd_HHmmss"));
	}

	protected void Init()
	{
		_sb = new StringBuilder();
	}

	protected void Save(FileOutputStream stream) throws IOException
	{
		OutputStreamWriter writer = new OutputStreamWriter(stream, "utf8");
		String s = _sb.toString();
		writer.write(s, 0, s.length());
		writer.close();
	}

	protected void WriteExerciseHeader(String name)
	{
		_sb.append(String.format("%s\r\n", name));
	}
	
	protected void WriteExerciseFooter(String name)
	{
		// no footer in texts
	}

	protected void WriteLogEntry(LocalDate date, ExerciseData entry)
	{
		_sb.append(String.format("\t%s\t%.1f\t%d\r\n", date.toString(), entry.getWeight(), entry.getReps()));
	}
	
	private StringBuilder _sb;
}
