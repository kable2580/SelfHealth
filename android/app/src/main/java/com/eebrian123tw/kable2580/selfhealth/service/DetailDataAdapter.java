package com.eebrian123tw.kable2580.selfhealth.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.eebrian123tw.kable2580.selfhealth.R;
import com.eebrian123tw.kable2580.selfhealth.dao.HealthDataDao;
import com.eebrian123tw.kable2580.selfhealth.service.entity.DailyDataModel;

import org.threeten.bp.LocalDate;

import java.text.DecimalFormat;
import java.util.List;

public class DetailDataAdapter extends RecyclerView.Adapter<DetailDataAdapter.ViewHolder> {

  public interface CallBack {
    void loadData();
  }

  private List<DetailDataUnit> detailData;
  private Context context;
  private CallBack callBack;

  public DetailDataAdapter(Context context, List<DetailDataUnit> detailData, CallBack callBack) {
    this.context = context;
    this.detailData = detailData;
    this.callBack = callBack;
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
    View view =
        LayoutInflater.from(context).inflate(R.layout.detail_data_unit_cardview, viewGroup, false);

    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i) {
    final DetailDataUnit detailDataUnit = detailData.get(i);
    LocalDate localDate = detailDataUnit.getDataTime();
    final DetailDataUnit.Type type = detailDataUnit.getType();
    String stringType;
    double value = detailDataUnit.getValue();
    DecimalFormat formatter = new DecimalFormat("#.###");
    formatter.applyPattern("0.000");
    switch (type) {
      case STEPS:
        stringType = context.getString(R.string.step);
        viewHolder.valueTextView.setText(String.valueOf(value));
        break;
      case DRINK:
        stringType = context.getString(R.string.drink);
        viewHolder.valueTextView.setText(String.valueOf(value));
        break;
      case SLEEP:
        stringType = context.getString(R.string.sleep);
        viewHolder.valueTextView.setText(formatter.format(value));

        break;
      case PHONE_USE:
        stringType = context.getString(R.string.phone_use);
        viewHolder.valueTextView.setText(formatter.format(value));
        break;
      default:
        stringType = context.getString(R.string.step);
        viewHolder.valueTextView.setText(String.valueOf(value));
    }

    viewHolder.dataTypeTextView.setText(stringType);

    viewHolder.dataTimeTextView.setText(localDate.toString());

    View.OnClickListener clickListener =
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            updateData(detailDataUnit, viewHolder);
          }
        };

    viewHolder.itemView.setOnClickListener(clickListener);
    switch (type) {
      case STEPS:
        viewHolder.itemView.setClickable(false);
    }
  }

  @SuppressLint("SetTextI18n")
  private void updateData(final DetailDataUnit detailDataUnit, final ViewHolder viewHolder) {
    final LocalDate localDate = detailDataUnit.getDataTime();
    final HealthDataDao healthDataDao = new HealthDataDao(context);
    DailyDataModel dailyDataModel = healthDataDao.getDailyData(localDate);
    dailyDataModel.setDataDate(localDate.toString());
    AlertDialog.Builder alert = new AlertDialog.Builder(context);
    final EditText edittext = new EditText(context);
    edittext.setInputType(
        InputType.TYPE_CLASS_NUMBER
            | InputType.TYPE_NUMBER_FLAG_DECIMAL
            | InputType.TYPE_NUMBER_FLAG_SIGNED);
    switch (detailDataUnit.getType()) {
      case STEPS:
        alert.setMessage(context.getString(R.string.step));
        edittext.setText(dailyDataModel.getSteps() + "");
        break;
      case SLEEP:
        alert.setMessage(context.getString(R.string.sleep));
        edittext.setText(dailyDataModel.getHoursOfSleep() + "");
        break;
      case DRINK:
        alert.setMessage(context.getString(R.string.drink));
        edittext.setText(dailyDataModel.getWaterCC() + "");
        break;
      case PHONE_USE:
        alert.setMessage(context.getString(R.string.phone_use));
        edittext.setText(dailyDataModel.getHoursPhoneUse() + "");
        break;
    }
    edittext.setSelection(edittext.getText().toString().length());
    alert.setTitle(localDate.toString());
    alert.setView(edittext);
    final DailyDataModel finalDailyDataModel = dailyDataModel;
    alert.setPositiveButton(
        context.getText(R.string.confirm),
        new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int whichButton) {

            try {

              switch (detailDataUnit.getType()) {
                case STEPS:
                  double value = Integer.parseInt(edittext.getText().toString());
                  finalDailyDataModel.setSteps((int) (value));
                  viewHolder.valueTextView.setText(value + "");
                  break;
                case SLEEP:
                  value = Double.parseDouble(edittext.getText().toString());
                  finalDailyDataModel.setHoursOfSleep(value);
                  viewHolder.valueTextView.setText(value + "");
                  break;
                case DRINK:
                  value = Integer.parseInt(edittext.getText().toString());
                  finalDailyDataModel.setWaterCC((int) value);
                  viewHolder.valueTextView.setText(value + "");
                  break;
                case PHONE_USE:
                  value = Double.parseDouble(edittext.getText().toString());
                  finalDailyDataModel.setHoursPhoneUse(value);
                  viewHolder.valueTextView.setText(value + "");
                  break;
              }
              healthDataDao.saveDailyData(finalDailyDataModel);
              callBack.loadData();
            } catch (Exception e) {
              e.printStackTrace();
            }
          }
        });
    alert.show();
  }

  @Override
  public int getItemCount() {
    return detailData.size();
  }

  class ViewHolder extends RecyclerView.ViewHolder {
    private TextView dataTimeTextView;
    private TextView dataTypeTextView;
    private TextView valueTextView;
    private View itemView;

    ViewHolder(@NonNull View itemView) {
      super(itemView);
      dataTimeTextView = itemView.findViewById(R.id.datatime_textview);
      dataTypeTextView = itemView.findViewById(R.id.data_type_textview);
      valueTextView = itemView.findViewById(R.id.value_textview);
      this.itemView = itemView;
    }
  }
}
