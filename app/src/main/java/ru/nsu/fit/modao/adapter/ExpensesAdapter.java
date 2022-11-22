package ru.nsu.fit.modao.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.nsu.fit.modao.R;
import ru.nsu.fit.modao.model.Currency;
import ru.nsu.fit.modao.model.Expenses;

public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesViewHolder> {
    Context context;
    List<Expenses> expenses;

    public ExpensesAdapter(Context context, List<Expenses> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @NonNull
    @Override
    public ExpensesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Какой дизайн использовать
        View expensesItem = LayoutInflater.from(context).inflate(R.layout.expense_item, parent, false);
        return new ExpensesViewHolder(expensesItem);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull ExpensesViewHolder holder, int position) {
        // Тут определяем, что куда подставлять
        // Этот кусок с if для определения, какую картинку подставлять
        StringBuilder sourceImageName = new StringBuilder("ic_");
        if (expenses.get(position).getExpense() >= 0){
            sourceImageName.append("profit_");
            //Тут если версия поддерживает (минимальная 23), то я получаю цвет из ресурсов
            // Иначе руками вписываю
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.expense.setTextColor(context.getResources().getColor(R.color.profit, null));
            }
            else {
                holder.expense.setTextColor(Color.parseColor("#6ED4AB"));
            }
        }
        else{
            sourceImageName.append("loss_");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                holder.expense.setTextColor(context.getResources().getColor(R.color.loss, null));
            }
            else {
                holder.expense.setTextColor(Color.parseColor("#D46E6E"));
            }
        }
        if (expenses.get(position).getCurrency() == Currency.RUB){
            sourceImageName.append("rub");
        }
        else {
            sourceImageName.append("dol");
        }
        // Перевод названия картинки в её id
        int imageId = context.getResources().getIdentifier(sourceImageName.toString(), "drawable", context.getPackageName());
        holder.currency.setImageResource(imageId);
        holder.expense.setText(String.format("%.2f", expenses.get(position).getExpense()));
        holder.shortInfo.setText(expenses.get(position).getShortInfo());

        //Обработчик нажатия
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showInfoAlert();
            }
        });

    }
    public void showInfoAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Info")
                .setMessage("In the process of development")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return expenses.size();
    }

    public static final class ExpensesViewHolder extends RecyclerView.ViewHolder{
        // Поля, с которыми будем взаимодействовать
        TextView expense;
        TextView shortInfo;
        ImageView currency;
        public ExpensesViewHolder(@NonNull View itemView) {
            super(itemView);
            // Привязываем поля к объектам, itemView можно воспринимать как отсылку к дизайну
            expense = itemView.findViewById(R.id.amountExpense);
            shortInfo = itemView.findViewById(R.id.shortInfo);
            currency = itemView.findViewById(R.id.currency);
        }
    }
}
