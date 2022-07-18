package com.example.expense.controller;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

import com.example.expense.db.DbHandler;
import com.example.expense.db.SQLiteDatabaseHelper;
import com.example.expense.model.Expenses;
import com.example.expense.model.ExpensesType;

import java.util.ArrayList;

public class ExpenseController extends BaseController {
    public static void ExpensesType(Context context, ArrayList<ExpensesType> expensesTypes) {


        boolean status = true;

        WRITE_LOCK.lock();
        SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper
                .getDatabaseInstance(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        try {

            database.beginTransaction();


            String dataInsertQuery = "REPLACE INTO tbl_expenses_type(" +
                    "    typeId , typeName ,  typeLimit ) values(?,?,?)";

            SQLiteStatement statement = database.compileStatement(dataInsertQuery);


            for (ExpensesType expensesType : expensesTypes) {
                DbHandler.performExecuteInsert(statement, new Object[]{
                        expensesType.getTypeId(), expensesType.getTypeName(), expensesType.getTypeLimit()});
            }


            database.setTransactionSuccessful();


        } catch (Exception e) {
            Log.e("error", e.toString());

        } finally {
            database.endTransaction();
            databaseHelper.close();
            WRITE_LOCK.unlock();
        }


    }

    public static boolean saveExpensesTypes(Context context, ArrayList<Expenses> expensesTypes) {


        boolean status = true;

        WRITE_LOCK.lock();
        SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper
                .getDatabaseInstance(context);
        SQLiteDatabase database = databaseHelper.getWritableDatabase();

        try {

            database.beginTransaction();


            String dataInsertQuery = "INSERT INTO tbl_expenses( expense_type , expense_limit ,  expense_phto ) values(?,?,?);";

            SQLiteStatement statement = database.compileStatement(dataInsertQuery);

            boolean b = false;
            for (Expenses expenses : expensesTypes) {
                 b = DbHandler.performExecuteInsert(statement, new Object[]{ expenses.getExpense_type(), expenses.getExpense_limit(), expenses.getExpense_phto()
                }) > 0;
            }



            database.setTransactionSuccessful();
            database.endTransaction();
            databaseHelper.close();
            WRITE_LOCK.unlock();
            return b;
        } catch (Exception e) {
            database.endTransaction();
            databaseHelper.close();
            WRITE_LOCK.unlock();
            return false;

        }

    }

    public static ArrayList<ExpensesType> getExpensesTypes(Context context) {

        READ_LOCK.lock();
        ArrayList<ExpensesType> expenses = new ArrayList<>();

        try {

            SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper
                    .getDatabaseInstance(context);
            SQLiteDatabase database = databaseHelper.getReadableDatabase();

            String query = "SELECT * from tbl_expenses_type";
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.moveToFirst()) {

                do {

                    ExpensesType expensesType = new ExpensesType(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
                    expenses.add(expensesType);


                } while (cursor.moveToNext());

            }
            cursor.close();


        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        } finally {
            READ_LOCK.unlock();
        }

        return expenses;

    }
    public static ArrayList<Expenses> getExpenses(Context context) {

        READ_LOCK.lock();
        ArrayList<Expenses> expenses = new ArrayList<>();

        try {

            SQLiteDatabaseHelper databaseHelper = SQLiteDatabaseHelper
                    .getDatabaseInstance(context);
            SQLiteDatabase database = databaseHelper.getReadableDatabase();

            String query = "SELECT * from tbl_expenses;";
            Cursor cursor = database.rawQuery(query, null);


            if (cursor.moveToFirst()) {

                do {

                    Expenses expensesType = new Expenses(cursor.getString(1), cursor.getDouble(2), cursor.getBlob(3));
                    expenses.add(expensesType);

                } while (cursor.moveToNext());

            }
            cursor.close();


        } catch (Exception e) {
            Log.e("ERROR", e.toString());
        } finally {
            READ_LOCK.unlock();
        }

        return expenses;

    }
}
