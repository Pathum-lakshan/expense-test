package com.example.expense.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.expense.R;
import com.example.expense.controller.ExpenseController;
import com.example.expense.model.Expenses;
import com.example.expense.model.ExpensesType;
import com.example.expense.model.User;
import com.example.expense.util.DbBitmapUtility;
import com.example.expense.util.SharedPref;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private Spinner spinner;
    private Intent intent;
    private ImageView expenseImage;
    private SharedPref sharedPref;

    private Bitmap expense_image;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private TextInputEditText textExpenseLimit;
    private Button save;
    NavigationView navigationView;

    private  double expensesLimit;
    private String expense_type;
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner= findViewById(R.id.expense_type_spinner);
        expenseImage= findViewById(R.id.customer_image);
        textExpenseLimit = findViewById(R.id.expense_limit);
        save = findViewById(R.id.save_button);
       // textExpenseLimit.setText("0.00");
        loadSpinner();
        sharedPref = new SharedPref(MainActivity.this);
        drawerLayout = findViewById(R.id.my_drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView = findViewById(R.id.navimamnnin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            Fragment fragment = null;
            @Override
            public boolean onNavigationItemSelected( MenuItem item) {
                switch (item.getItemId()){
                    case R.id.log:
                        sharedPref.clear();
                        intent = new Intent(MainActivity.this, login.class);
                        startActivity(intent);
                        finish();
                        break;

                    case R.id.email:

                        break;

                    case R.id.money:

                        break;

                }
                return true;
            }
        });


        save.setOnClickListener(view -> {


            String expense = textExpenseLimit.getText().toString();

            if (expense.matches("")||expense_image==null){

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle(R.string.app_name);
                alertDialogBuilder.setMessage("INCORRECT Input  ");
                alertDialogBuilder.setPositiveButton("OK", null);
                alertDialogBuilder.show();

            }else {
                ArrayList<Expenses> expenses = new ArrayList<>();


                expenses.add(new Expenses(expense_type,Double.parseDouble(textExpenseLimit.getText().toString()), DbBitmapUtility.getBytes(expense_image)));

                double enterExpenses = Double.parseDouble(expense);


                if (expensesLimit>enterExpenses){
                    if (ExpenseController.saveExpensesTypes(MainActivity.this,expenses)){
                        Toast.makeText(MainActivity.this, "Expense Saved", Toast.LENGTH_SHORT).show();
                        expenseImage.setImageBitmap(null);
                        textExpenseLimit.setText("");
                    }else {

                        Toast.makeText(MainActivity.this, "Expense not Saved", Toast.LENGTH_SHORT).show();

                    }

                }else {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                    alertDialogBuilder.setTitle(R.string.app_name);
                    alertDialogBuilder.setMessage("INCORRECT Input  ");
                    alertDialogBuilder.setPositiveButton("OK", null);
                    alertDialogBuilder.show();
                }


            }
        });

        expenseImage.setOnClickListener(view -> {

            takeImage();


        });
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                if (result.getResultCode() == RESULT_OK && result.getData() != null){
                    Bundle bundle = result.getData().getExtras();
                    expense_image = (Bitmap) bundle.get("data");
                }
                expenseImage.setImageBitmap(expense_image);
            }
        });
    }


    @Override
    public boolean onOptionsItemSelected( MenuItem item) {

        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadSpinner() {
        SpinnerAdapter  spinnerAdapter = new SpinnerAdapter(this, ExpenseController.getExpensesTypes(this));

        spinner.setAdapter(spinnerAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                ExpensesType itemAtPosition = (ExpensesType) parent.getItemAtPosition(position);

                expense_type = itemAtPosition.getTypeName();
                expensesLimit=itemAtPosition.getTypeLimit();

            }
            @Override
            public void onNothingSelected(AdapterView <?> parent) {

            }
        });

    }

    private void takeImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        activityResultLauncher.launch(takePictureIntent);

    }
    private void callFragment(Fragment fragment){
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        transaction.replace(R.id.fragment_loader, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
class SpinnerAdapter extends BaseAdapter {

    private ArrayList<ExpensesType> expensesTypes;
    private LayoutInflater inflater;

    SpinnerAdapter (Context context, ArrayList<ExpensesType> expensesTypes){
        inflater= (LayoutInflater) context.getSystemService((Context.LAYOUT_INFLATER_SERVICE));

        this.expensesTypes=expensesTypes;
    }

    @Override
    public int getCount() {
        return expensesTypes.size();
    }

    @Override
    public ExpensesType getItem(int i) {
        return expensesTypes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        Holders holder = null;


        if (view == null){
            holder = new Holders();
            view = inflater.inflate(R.layout.spiner_expenses, null);

            holder.type_id = view.findViewById(R.id.type_id);
            holder.type_name = view.findViewById(R.id.type_name);
            holder.type_limit = view.findViewById(R.id.type_limit);
        }else {
            holder=(Holders) view.getTag();
        }

        ExpensesType expensesTypes = getItem(i);

        holder.type_id.setText(String.valueOf(expensesTypes.getTypeId()));
        holder.type_name.setText(expensesTypes.getTypeName());
        holder.type_limit.setText( String.valueOf(expensesTypes.getTypeLimit()));

        view.setTag(holder);
        return view;

    }
}
class Holders{
    TextView type_id;
    TextView type_name;
    TextView type_limit;

}