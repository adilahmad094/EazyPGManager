package net.eazypg.eazypgmanager.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailAC;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailCCTV;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailD2H;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailDishwasher;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailFan;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailGeyser;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailHeater;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailInduction;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailIron;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailLift;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailMicrowave;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailOther;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailRO;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailRefrigerator;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailRouter;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailTV;
import net.eazypg.eazypgmanager.ApplianceDetail.ApplianceDetailWashingMachine;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.ACDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.CCTVDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.D2HDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.DishwasherDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.FanDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.GeyserDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.HeaterDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.InductionDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.IronDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.LiftDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.MicrowaveDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.OtherDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.RODetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.RouterDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.TVDetailList;
import net.eazypg.eazypgmanager.DetailList.ApplianceDetailList.WashingMachineDetailList;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.ACDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.CCTVDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.D2HDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.DishwasherDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.FanDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.GeyserDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.HeaterDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.InductionDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.IronDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.LiftDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.MicrowaveDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.OtherApplianceDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RODetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RefrigeratorDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.RouterDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.TVDetails;
import net.eazypg.eazypgmanager.DetailsClasses.ApplianceDetailClasses.WashingMachineDetails;
import net.eazypg.eazypgmanager.DetailsClasses.RoomApplianceDetails;
import net.eazypg.eazypgmanager.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fabric.sdk.android.Fabric;


public class ApplianceDetailsActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;
    FloatingActionButton addButton;

    ImageView backButton;

    boolean flag;

    EditText ACRoomNo, ACCompanyName, ACModel, ACCapacity, ACStarRating, ACType;
    EditText FanRoomNo, FanCompanyName, FanModel, FanBlades;
    EditText LiftCompanyName, LiftModel, LiftCapacity, LiftDoor;
    EditText GeyserRoomNo, GeyserCompanyName, GeyserModel, GeyserCapacity, GeyserPower, GeyserRating;
    EditText WashingMachineRoomNo, WashingMachineCompanyName, WashingMachineModel, WashingMachineCapacity, WashingMachinePower, WashingMachineRating, WashingMachineType;
    EditText RORoomNo, ROCompanyName, ROModel, ROCapacity;
    EditText DishwasherRoomNo, DishwasherCompanyName, DishwasherModel, DishwasherCapacity, DishwasherType;
    EditText MicrowaveRoomNo, MicrowaveCompanyName, MicrowaveModel, MicrowaveCapacity, MicrowaveType;
    EditText FridgeRoomNo, FridgeCompanyName, FridgeModel, FridgeCapacity, FridgeType, FridgeRating;
    EditText TVRoomNo, TVCompanyName, TVModel, TVType, TVSize, TVResolution;
    EditText CCTVRoomNo, CCTVCompanyName, CCTVModel, CCTVNight, CCTVChanel, CCTVResolution;
    EditText IronRoomNo, IronCompanyName, IronModel, IronPower;
    EditText InductionRoomNo, InductionCompanyName, InductionModel, InductionPower, InductionType, InductionNoCooktop;
    EditText RouterRoomNo, RouterCompanyName, RouterModel, RouterAntenna, RouterSpeed;
    EditText HeaterRoomNo, HeaterCompanyName, HeaterModel, HeaterPower, HeaterWeight;
    EditText D2HRoomNo, D2HCompanyName;
    EditText OtherRoomNo, OtherName, OtherCompanyName;
    TextView ACLastServiceDate, FanDays, LiftDays, GeyserDays, WashingMachineDays, RODays, DishwasherDays, MicrowaveDays, FridgeDays, TVDays, CCTVDays, IronDays, InductionDays, RouterDays, HeaterDays, D2HDays;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference, databaseReference1, databaseReference2;
    FirebaseUser firebaseUser;

    CircleImageView applianceImage;

    Bitmap bitmap;

    ConstraintLayout emptyLayout;
    TextView customTitle;

    ListView listViewAppliances;
    List<String> rooms;

    List<ApplianceDetailAC> acDetailsList;
    List<ApplianceDetailCCTV> cctvDetailsList;
    List<ApplianceDetailD2H> d2hDetailsList;
    List<ApplianceDetailDishwasher> dishwasherDetailsList;
    List<ApplianceDetailFan> fanDetailsList;
    List<ApplianceDetailGeyser> geyserDetailsList;
    List<ApplianceDetailHeater> heaterDetailsList;
    List<ApplianceDetailInduction> inductionDetailsList;
    List<ApplianceDetailIron> ironDetailsList;
    List<ApplianceDetailLift> liftDetailsList;
    List<ApplianceDetailMicrowave> microwaveDetailsList;
    List<ApplianceDetailRefrigerator> fridgeDetailsList;
    List<ApplianceDetailRO> roDetailsList;
    List<ApplianceDetailRouter> routerDetailsList;
    List<ApplianceDetailTV> tvDetailsList;
    List<ApplianceDetailWashingMachine> washingMachineDetailsList;
    List<ApplianceDetailOther> otherDetailsList;


    RelativeLayout ACLayout, fanLayout, liftLayout, geyserLayout, washingMachineLayout, ROLayout, dishwasherLayout, microwaveLayout,
            fridgeLayout, TVLayout, CCTVLayout, ironLayout, inductionLayout, routerLayout, heaterLayout, D2HLayout, otherLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appliance_details);

        Fabric.with(this, new Crashlytics());


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.rgb(33,33,33));
        }

        backButton = findViewById(R.id.backButton);



        emptyLayout = findViewById(R.id.emptyAppliancesLayout);

        applianceImage = findViewById(R.id.applianceImageView);

        listView = findViewById(R.id.listViewAppliances);
        listView.setEmptyView(emptyLayout);
        textView = findViewById(R.id.applianceNameTextView);
        addButton = findViewById(R.id.addButton);

        rooms = new ArrayList<>();

        listViewAppliances = findViewById(R.id.listViewAppliances);

        acDetailsList = new ArrayList<>();
        cctvDetailsList = new ArrayList<>();
        d2hDetailsList = new ArrayList<>();
        dishwasherDetailsList = new ArrayList<>();
        fanDetailsList = new ArrayList<>();
        geyserDetailsList = new ArrayList<>();
        heaterDetailsList = new ArrayList<>();
        inductionDetailsList = new ArrayList<>();
        ironDetailsList = new ArrayList<>();
        liftDetailsList = new ArrayList<>();
        microwaveDetailsList = new ArrayList<>();
        fridgeDetailsList = new ArrayList<>();
        roDetailsList = new ArrayList<>();
        routerDetailsList = new ArrayList<>();
        tvDetailsList = new ArrayList<>();
        washingMachineDetailsList = new ArrayList<>();
        otherDetailsList = new ArrayList<>();

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();

        Intent intent = getIntent();
        final String applianceName = intent.getStringExtra(ApplianceActivity.EXTRA_MESSAGE);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ApplianceDetailsActivity.this, ApplianceActivity.class));
                finish();
            }
        });

        databaseReference2 = firebaseDatabase.getReference("PG/" + FirebaseAuth.getInstance().getCurrentUser().getUid() + "/Rooms/");

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rooms.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    rooms.add(snapshot.getKey());
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        switch (applianceName) {

                case "AC" :

                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.ac);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/AC");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            acDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailAC acDetails = snapshot.getValue(ApplianceDetailAC.class);
                                acDetailsList.add(acDetails);

                            }

                            ACDetailList adapter = new ACDetailList(ApplianceDetailsActivity.this, acDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });


                break;


                case "CCTV" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.cctv);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/CCTV");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            cctvDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailCCTV cctvDetails = snapshot.getValue(ApplianceDetailCCTV.class);
                                cctvDetailsList.add(cctvDetails);

                            }

                            CCTVDetailList adapter = new CCTVDetailList(ApplianceDetailsActivity.this, cctvDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;

                case "Dishwasher" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.dishwashing);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Dishwasher");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            dishwasherDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailDishwasher dishwasherDetails = snapshot.getValue(ApplianceDetailDishwasher.class);
                                dishwasherDetailsList.add(dishwasherDetails);

                            }

                            DishwasherDetailList adapter = new DishwasherDetailList(ApplianceDetailsActivity.this, dishwasherDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;

                case "Fan" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.fan);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Fan");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            fanDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailFan fanDetails = snapshot.getValue(ApplianceDetailFan.class);
                                fanDetailsList.add(fanDetails);

                            }

                            FanDetailList adapter = new FanDetailList(ApplianceDetailsActivity.this, fanDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                 case "Geyser" :
                     bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.geyser);
                     applianceImage.setImageBitmap(bitmap);

                     databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Geyser");
                     databaseReference1.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             geyserDetailsList.clear();

                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                 ApplianceDetailGeyser geyserDetails = snapshot.getValue(ApplianceDetailGeyser.class);
                                 geyserDetailsList.add(geyserDetails);

                             }

                             GeyserDetailList adapter = new GeyserDetailList(ApplianceDetailsActivity.this, geyserDetailsList);
                             listViewAppliances.setAdapter(adapter);


                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });

                     break;

                 case "Heater" :
                     bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.heaterhot);
                     applianceImage.setImageBitmap(bitmap);

                     databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Heater");
                     databaseReference1.addValueEventListener(new ValueEventListener() {
                         @Override
                         public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                             heaterDetailsList.clear();

                             for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                 ApplianceDetailHeater heaterDetails = snapshot.getValue(ApplianceDetailHeater.class);
                                 heaterDetailsList.add(heaterDetails);

                             }

                             HeaterDetailList adapter = new HeaterDetailList(ApplianceDetailsActivity.this, heaterDetailsList);
                             listViewAppliances.setAdapter(adapter);


                         }

                         @Override
                         public void onCancelled(@NonNull DatabaseError databaseError) {

                         }
                     });

                     break;


                case "Induction" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.induction_hot);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Induction");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            inductionDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailInduction inductionDetails = snapshot.getValue(ApplianceDetailInduction.class);
                                inductionDetailsList.add(inductionDetails);

                            }

                            InductionDetailList adapter = new InductionDetailList(ApplianceDetailsActivity.this, inductionDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "Iron" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.iron_hot);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Iron");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            ironDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailIron ironDetails = snapshot.getValue(ApplianceDetailIron.class);
                                ironDetailsList.add(ironDetails);

                            }

                            IronDetailList adapter = new IronDetailList(ApplianceDetailsActivity.this, ironDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "Lift" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.lift);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Lift");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            liftDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailLift liftDetails = snapshot.getValue(ApplianceDetailLift.class);
                                liftDetailsList.add(liftDetails);

                            }

                            LiftDetailList adapter = new LiftDetailList(ApplianceDetailsActivity.this, liftDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "Microwave" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.microwave);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Microwave");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            microwaveDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailMicrowave microwaveDetails = snapshot.getValue(ApplianceDetailMicrowave.class);
                                microwaveDetailsList.add(microwaveDetails);

                            }

                            MicrowaveDetailList adapter = new MicrowaveDetailList(ApplianceDetailsActivity.this, microwaveDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "Refrigerator" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.fridge);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Refrigerator");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            microwaveDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailMicrowave microwaveDetails = snapshot.getValue(ApplianceDetailMicrowave.class);
                                microwaveDetailsList.add(microwaveDetails);

                            }

                            MicrowaveDetailList adapter = new MicrowaveDetailList(ApplianceDetailsActivity.this, microwaveDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "RO" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.ro);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/RO");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            roDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailRO roDetails = snapshot.getValue(ApplianceDetailRO.class);
                                roDetailsList.add(roDetails);

                            }

                            RODetailList adapter = new RODetailList(ApplianceDetailsActivity.this, roDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "Router" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.router);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Router");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            routerDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailRouter routerDetails = snapshot.getValue(ApplianceDetailRouter.class);
                                routerDetailsList.add(routerDetails);

                            }

                            RouterDetailList adapter = new RouterDetailList(ApplianceDetailsActivity.this, routerDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "TV" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.tv);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/TV");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            tvDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailTV tvDetails = snapshot.getValue(ApplianceDetailTV.class);
                                tvDetailsList.add(tvDetails);

                            }

                            TVDetailList adapter = new TVDetailList(ApplianceDetailsActivity.this, tvDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;


                case "Washing Machine" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.washinemachine);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Washing Machine");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            washingMachineDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailWashingMachine washingMachineDetails = snapshot.getValue(ApplianceDetailWashingMachine.class);
                                washingMachineDetailsList.add(washingMachineDetails);

                            }

                            WashingMachineDetailList adapter = new WashingMachineDetailList(ApplianceDetailsActivity.this, washingMachineDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;

                    case "d2h" :
                     bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.d2h);
                     applianceImage.setImageBitmap(bitmap);

                        databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/D2H");
                        databaseReference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                d2hDetailsList.clear();

                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                    ApplianceDetailD2H d2HDetails = snapshot.getValue(ApplianceDetailD2H.class);
                                    d2hDetailsList.add(d2HDetails);

                                }

                                D2HDetailList adapter = new D2HDetailList(ApplianceDetailsActivity.this, d2hDetailsList);
                                listViewAppliances.setAdapter(adapter);


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    break;


                case "Other" :
                    bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources() ,R.drawable.more);
                    applianceImage.setImageBitmap(bitmap);

                    databaseReference1 = firebaseDatabase.getReference("PG/" + firebaseUser.getUid() + "/Appliances/Other");
                    databaseReference1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                            otherDetailsList.clear();

                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {

                                ApplianceDetailOther otherDetails = snapshot.getValue(ApplianceDetailOther.class);
                                otherDetailsList.add(otherDetails);

                            }

                            OtherDetailList adapter = new OtherDetailList(ApplianceDetailsActivity.this, otherDetailsList);
                            listViewAppliances.setAdapter(adapter);


                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    break;




        }

        databaseReference = firebaseDatabase.getReference("PG/" + firebaseUser.getUid());

        textView.setText(applianceName);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            showDialog(applianceName);

            }
        });

    }

    private void showDialog(final String applianceName) {

        LayoutInflater inflater;
        inflater = getLayoutInflater();

        final View view = inflater.inflate(R.layout.dialog_appliance, null);

        ACLayout = view.findViewById(R.id.ACLayout);
        fanLayout = view.findViewById(R.id.fanLayout);
        liftLayout = view.findViewById(R.id.liftLayout);
        geyserLayout = view.findViewById(R.id.geyserLayout);
        washingMachineLayout = view.findViewById(R.id.washingMachineLayout);
        ROLayout = view.findViewById(R.id.ROLayout);
        dishwasherLayout = view.findViewById(R.id.dishwasherLayout);
        microwaveLayout = view.findViewById(R.id.microwaveLayout);
        fridgeLayout = view.findViewById(R.id.fridgeLayout);
        TVLayout = view.findViewById(R.id.TVLayout);
        CCTVLayout = view.findViewById(R.id.CCTVLayout);
        ironLayout = view.findViewById(R.id.ironLayout);
        inductionLayout = view.findViewById(R.id.inductionLayout);
        routerLayout = view.findViewById(R.id.routerLayout);
        heaterLayout = view.findViewById(R.id.heaterLayout);
        D2HLayout = view.findViewById(R.id.D2HLayout);
        otherLayout = view.findViewById(R.id.otherLayout);

        final View titleView = inflater.inflate(R.layout.custom_title1, null);
        customTitle = titleView.findViewById(R.id.custom_title);

        AlertDialog.Builder builder = new AlertDialog.Builder(ApplianceDetailsActivity.this);
        builder.setCustomTitle(customTitle);

        builder.setView(view);

        switch (applianceName) {
            case "AC":

                if(ACLayout.getParent().getParent()!=null) {
                    ((ViewGroup) ACLayout.getParent().getParent()).removeView((ViewGroup)ACLayout.getParent());
                }
                ACLayout.setVisibility(View.VISIBLE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                ACLastServiceDate = view.findViewById(R.id.ACLastServiceDateEditText);

                final Calendar calendar = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(Calendar.YEAR, i);
                        calendar.set(Calendar.MONTH, i1);
                        calendar.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        ACLastServiceDate.setText(sdf.format(calendar.getTime()));
                    }
                };

                ACLastServiceDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, date, calendar
                                .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Fan":

                if(fanLayout.getParent().getParent()!=null) {
                    ((ViewGroup) fanLayout.getParent().getParent()).removeView((ViewGroup)fanLayout.getParent());
                }

                FanDays = view.findViewById(R.id.fanDaysEditText);

                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.VISIBLE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                final Calendar calendarFan = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateFan = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarFan.set(Calendar.YEAR, i);
                        calendarFan.set(Calendar.MONTH, i1);
                        calendarFan.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        FanDays.setText(sdf.format(calendarFan.getTime()));
                    }
                };

                FanDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateFan, calendarFan
                                .get(Calendar.YEAR), calendarFan.get(Calendar.MONTH),
                                calendarFan.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Lift":

                if(liftLayout.getParent().getParent()!=null) {
                    ((ViewGroup) liftLayout.getParent().getParent()).removeView((ViewGroup)liftLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.VISIBLE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                LiftDays = view.findViewById(R.id.liftDaysEditText);

                final Calendar calendarLift = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateLift = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarLift.set(Calendar.YEAR, i);
                        calendarLift.set(Calendar.MONTH, i1);
                        calendarLift.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        LiftDays.setText(sdf.format(calendarLift.getTime()));
                    }
                };

                LiftDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateLift, calendarLift
                                .get(Calendar.YEAR), calendarLift.get(Calendar.MONTH),
                                calendarLift.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Geyser":

                if(geyserLayout.getParent().getParent()!=null) {
                    ((ViewGroup) geyserLayout.getParent().getParent()).removeView((ViewGroup)geyserLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.VISIBLE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                GeyserDays = view.findViewById(R.id.geyserDaysEditText);

                final Calendar calendarGeyser = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateGeyser = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarGeyser.set(Calendar.YEAR, i);
                        calendarGeyser.set(Calendar.MONTH, i1);
                        calendarGeyser.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        GeyserDays.setText(sdf.format(calendarGeyser.getTime()));
                    }
                };

                GeyserDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateGeyser, calendarGeyser
                                .get(Calendar.YEAR), calendarGeyser.get(Calendar.MONTH),
                                calendarGeyser.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Washing Machine":

                if(washingMachineLayout.getParent().getParent()!=null) {
                    ((ViewGroup) washingMachineLayout.getParent().getParent()).removeView((ViewGroup)washingMachineLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.VISIBLE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                WashingMachineDays = view.findViewById(R.id.wmDaysEditText);

                final Calendar calendarWM = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateWM = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarWM.set(Calendar.YEAR, i);
                        calendarWM.set(Calendar.MONTH, i1);
                        calendarWM.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        WashingMachineDays.setText(sdf.format(calendarWM.getTime()));
                    }
                };

                WashingMachineDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateWM, calendarWM
                                .get(Calendar.YEAR), calendarWM.get(Calendar.MONTH),
                                calendarWM.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "RO":

                if(ROLayout.getParent().getParent()!=null) {
                    ((ViewGroup) ROLayout.getParent().getParent()).removeView((ViewGroup)ROLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.VISIBLE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                RODays = view.findViewById(R.id.RODaysEditText);

                final Calendar calendarRO = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateRO = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarRO.set(Calendar.YEAR, i);
                        calendarRO.set(Calendar.MONTH, i1);
                        calendarRO.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        RODays.setText(sdf.format(calendarRO.getTime()));
                    }
                };

                RODays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateRO, calendarRO
                                .get(Calendar.YEAR), calendarRO.get(Calendar.MONTH),
                                calendarRO.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;

            case "Dishwasher":
                if(dishwasherLayout.getParent().getParent()!=null) {
                    ((ViewGroup) dishwasherLayout.getParent().getParent()).removeView((ViewGroup)dishwasherLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.VISIBLE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                DishwasherDays = view.findViewById(R.id.dishwasherDaysEditText);

                final Calendar calendarDishwasher = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateDishwasher = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarDishwasher.set(Calendar.YEAR, i);
                        calendarDishwasher.set(Calendar.MONTH, i1);
                        calendarDishwasher.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        DishwasherDays.setText(sdf.format(calendarDishwasher.getTime()));
                    }
                };

                DishwasherDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateDishwasher, calendarDishwasher
                                .get(Calendar.YEAR), calendarDishwasher.get(Calendar.MONTH),
                                calendarDishwasher.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Microwave":
                if(microwaveLayout.getParent().getParent()!=null) {
                    ((ViewGroup) ACLayout.getParent().getParent()).removeView((ViewGroup)microwaveLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.VISIBLE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                MicrowaveDays = view.findViewById(R.id.microwaveDaysEditText);

                final Calendar calendarMicrowave = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateMicrowave = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarMicrowave.set(Calendar.YEAR, i);
                        calendarMicrowave.set(Calendar.MONTH, i1);
                        calendarMicrowave.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        MicrowaveDays.setText(sdf.format(calendarMicrowave.getTime()));
                    }
                };

                MicrowaveDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateMicrowave, calendarMicrowave
                                .get(Calendar.YEAR), calendarMicrowave.get(Calendar.MONTH),
                                calendarMicrowave.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Refrigerator":
                if(fridgeLayout.getParent().getParent()!=null) {
                    ((ViewGroup) fridgeLayout.getParent().getParent()).removeView((ViewGroup)fridgeLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.VISIBLE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                FridgeDays = view.findViewById(R.id.fridgeDaysEditText);

                final Calendar calendarFridge = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateFridge = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarFridge.set(Calendar.YEAR, i);
                        calendarFridge.set(Calendar.MONTH, i1);
                        calendarFridge.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        FridgeDays.setText(sdf.format(calendarFridge.getTime()));
                    }
                };

                FridgeDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateFridge, calendarFridge
                                .get(Calendar.YEAR), calendarFridge.get(Calendar.MONTH),
                                calendarFridge.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "TV":
                if(TVLayout.getParent().getParent()!=null) {
                    ((ViewGroup) TVLayout.getParent().getParent()).removeView((ViewGroup)TVLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.VISIBLE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                TVDays = view.findViewById(R.id.TVDaysEditText);

                final Calendar calendarTV = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateTV = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarTV.set(Calendar.YEAR, i);
                        calendarTV.set(Calendar.MONTH, i1);
                        calendarTV.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        TVDays.setText(sdf.format(calendarTV.getTime()));
                    }
                };

                TVDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateTV, calendarTV
                                .get(Calendar.YEAR), calendarTV.get(Calendar.MONTH),
                                calendarTV.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "CCTV":
                if(CCTVLayout.getParent().getParent()!=null) {
                    ((ViewGroup) CCTVLayout.getParent().getParent()).removeView((ViewGroup)CCTVLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.VISIBLE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                CCTVDays = view.findViewById(R.id.CCTVDaysEditText);

                final Calendar calendarCCTV = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateCCTV = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarCCTV.set(Calendar.YEAR, i);
                        calendarCCTV.set(Calendar.MONTH, i1);
                        calendarCCTV.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        CCTVDays.setText(sdf.format(calendarCCTV.getTime()));
                    }
                };

                CCTVDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateCCTV, calendarCCTV
                                .get(Calendar.YEAR), calendarCCTV.get(Calendar.MONTH),
                                calendarCCTV.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Iron":
                if(ironLayout.getParent().getParent()!=null) {
                    ((ViewGroup) ironLayout.getParent().getParent()).removeView((ViewGroup)ironLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.VISIBLE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                IronDays = view.findViewById(R.id.ironDaysEditText);

                final Calendar calendarIron = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateIron = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarIron.set(Calendar.YEAR, i);
                        calendarIron.set(Calendar.MONTH, i1);
                        calendarIron.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        IronDays.setText(sdf.format(calendarIron.getTime()));
                    }
                };

                IronDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateIron, calendarIron
                                .get(Calendar.YEAR), calendarIron.get(Calendar.MONTH),
                                calendarIron.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;

            case "Induction":

                if(inductionLayout.getParent().getParent()!=null) {
                    ((ViewGroup) inductionLayout.getParent().getParent()).removeView((ViewGroup)inductionLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.VISIBLE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                InductionDays = view.findViewById(R.id.inductionDaysEditText);

                final Calendar calendarInduction = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateInduction = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarInduction.set(Calendar.YEAR, i);
                        calendarInduction.set(Calendar.MONTH, i1);
                        calendarInduction.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        InductionDays.setText(sdf.format(calendarInduction.getTime()));
                    }
                };

                InductionDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateInduction, calendarInduction
                                .get(Calendar.YEAR), calendarInduction.get(Calendar.MONTH),
                                calendarInduction.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Router":

                if(routerLayout.getParent().getParent()!=null) {
                    ((ViewGroup) routerLayout.getParent().getParent()).removeView((ViewGroup)routerLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.VISIBLE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                RouterDays = view.findViewById(R.id.routerDaysEditText);

                final Calendar calendarRouter = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateRouter = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarRouter.set(Calendar.YEAR, i);
                        calendarRouter.set(Calendar.MONTH, i1);
                        calendarRouter.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        RouterDays.setText(sdf.format(calendarRouter.getTime()));
                    }
                };

                RouterDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateRouter, calendarRouter
                                .get(Calendar.YEAR), calendarRouter.get(Calendar.MONTH),
                                calendarRouter.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Heater":

                if(heaterLayout.getParent().getParent()!=null) {
                    ((ViewGroup) heaterLayout.getParent().getParent()).removeView((ViewGroup)heaterLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.VISIBLE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.GONE);

                HeaterDays = view.findViewById(R.id.heaterDaysEditText);

                final Calendar calendarHeater = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateHeater = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarHeater.set(Calendar.YEAR, i);
                        calendarHeater.set(Calendar.MONTH, i1);
                        calendarHeater.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        HeaterDays.setText(sdf.format(calendarHeater.getTime()));
                    }
                };

                HeaterDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateHeater, calendarHeater
                                .get(Calendar.YEAR), calendarHeater.get(Calendar.MONTH),
                                calendarHeater.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "D2H":
                if(D2HLayout.getParent().getParent()!=null) {
                    ((ViewGroup) D2HLayout.getParent().getParent()).removeView((ViewGroup)D2HLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.VISIBLE);
                otherLayout.setVisibility(View.GONE);

                D2HDays = view.findViewById(R.id.D2HDaysEditText);

                final Calendar calendarD2H = Calendar.getInstance();

                final DatePickerDialog.OnDateSetListener dateD2H = new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendarD2H.set(Calendar.YEAR, i);
                        calendarD2H.set(Calendar.MONTH, i1);
                        calendarD2H.set(Calendar.DAY_OF_MONTH, i2);

                        String myFormat = "dd/MM/yy";
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

                        D2HDays.setText(sdf.format(calendarD2H.getTime()));
                    }
                };

                D2HDays.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        new DatePickerDialog(ApplianceDetailsActivity.this, dateD2H, calendarD2H
                                .get(Calendar.YEAR), calendarD2H.get(Calendar.MONTH),
                                calendarD2H.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                break;
            case "Other":
                if(otherLayout.getParent().getParent()!=null) {
                    ((ViewGroup) otherLayout.getParent().getParent()).removeView((ViewGroup)otherLayout.getParent());
                }
                ACLayout.setVisibility(View.GONE);
                fanLayout.setVisibility(View.GONE);
                liftLayout.setVisibility(View.GONE);
                geyserLayout.setVisibility(View.GONE);
                washingMachineLayout.setVisibility(View.GONE);
                ROLayout.setVisibility(View.GONE);
                dishwasherLayout.setVisibility(View.GONE);
                microwaveLayout.setVisibility(View.GONE);
                fridgeLayout.setVisibility(View.GONE);
                TVLayout.setVisibility(View.GONE);
                CCTVLayout.setVisibility(View.GONE);
                ironLayout.setVisibility(View.GONE);
                inductionLayout.setVisibility(View.GONE);
                routerLayout.setVisibility(View.GONE);
                heaterLayout.setVisibility(View.GONE);
                D2HLayout.setVisibility(View.GONE);
                otherLayout.setVisibility(View.VISIBLE);

                break;
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ConnectivityManager connectivityManager
                        = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {

                    final ProgressDialog progressDialog = ProgressDialog.show(ApplianceDetailsActivity.this, "", "Saving...", true);

                    switch (applianceName) {
                        case "AC":

                            ACRoomNo = view.findViewById(R.id.ACRoomNumberEditText);
                            ACCompanyName = view.findViewById(R.id.ACCompanyNameEditText);
                            ACModel = view.findViewById(R.id.ACModelEditText);
                            ACCapacity = view.findViewById(R.id.ACCapacityEditText);
                            ACLastServiceDate = view.findViewById(R.id.ACLastServiceDateEditText);
                            ACStarRating = view.findViewById(R.id.ACStarRatingEditText);
                            ACType = view.findViewById(R.id.ACTypeEditText);

                            String roomNoAC = ACRoomNo.getText().toString();
                            String brandAC = ACCompanyName.getText().toString();
                            String modelAC = ACModel.getText().toString();
                            String capacityAC = ACCapacity.getText().toString();
                            String lastServiceDateAC = ACLastServiceDate.getText().toString();
                            String starRatingAC = ACStarRating.getText().toString();
                            String typeAC = ACType.getText().toString();
                            String uidAC = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoAC.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoAC + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }

                            else {

                                if (roomNoAC.equals("")) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ApplianceDetailsActivity.this, "Room Number Required", Toast.LENGTH_SHORT).show();
                                } else {

                                    if (roomNoAC != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidAC, "AC", brandAC, typeAC);
                                        databaseReference.child("Rooms").child(roomNoAC).child("Appliance").child(uidAC).setValue(roomApplianceDetails);
                                    }
                                    ACDetails acDetails = new ACDetails(uidAC, roomNoAC, brandAC, modelAC, capacityAC, lastServiceDateAC, starRatingAC, typeAC);
                                    databaseReference.child("Appliances").child("AC").child(uidAC).setValue(acDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }

                            break;
                        case "Fan":

                            FanRoomNo = view.findViewById(R.id.fanRoomNumberEditText);
                            FanCompanyName = view.findViewById(R.id.fanCompanyNameEditText);
                            FanModel = view.findViewById(R.id.fanModelEditText);
                            FanBlades = view.findViewById(R.id.fanBladesEditText);

                            String roomNoFan = FanRoomNo.getText().toString();
                            String brandFan = FanCompanyName.getText().toString();
                            String modelFan = FanModel.getText().toString();
                            String timeSinceInstallationFan = FanDays.getText().toString();
                            String noOfBladesFan = FanBlades.getText().toString();
                            String uidFan = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoFan.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoFan + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }

                            else {

                                if (roomNoFan.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Room number Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoFan != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidFan, "Fan", brandFan, timeSinceInstallationFan);
                                        databaseReference.child("Rooms").child(roomNoFan).child("Appliance").child(uidFan).setValue(roomApplianceDetails);
                                    }
                                    FanDetails fanDetails = new FanDetails(uidFan, roomNoFan, brandFan, modelFan, timeSinceInstallationFan, noOfBladesFan);
                                    databaseReference.child("Appliances").child("Fan").child(uidFan).setValue(fanDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }

                            break;
                        case "Lift":

                            LiftCompanyName = view.findViewById(R.id.liftCompanyNameEditText);
                            LiftModel = view.findViewById(R.id.liftModelEditText);
                            LiftCapacity = view.findViewById(R.id.liftCapacityEditText);
                            LiftDoor = view.findViewById(R.id.liftDoorEditText);

                            String brandLift = LiftCompanyName.getText().toString();
                            String modelLift = LiftModel.getText().toString();
                            String daysLift = LiftDays.getText().toString();
                            String capacityLift = LiftCapacity.getText().toString();
                            String doorLift = LiftDoor.getText().toString();
                            String uidLift = databaseReference.push().getKey();

                            LiftDetails liftDetails = new LiftDetails(uidLift, brandLift, modelLift, daysLift, capacityLift, doorLift);

                            databaseReference.child("Appliances").child("Lift").child(uidLift).setValue(liftDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                }
                            });


                            break;
                        case "Geyser":

                            GeyserRoomNo = view.findViewById(R.id.geyserRoomNumberEditText);
                            GeyserCompanyName = view.findViewById(R.id.geyserCompanyNameEditText);
                            GeyserModel = view.findViewById(R.id.geyserModelEditText);
                            GeyserCapacity = view.findViewById(R.id.geyserCapacityEditText);
                            GeyserPower = view.findViewById(R.id.geyserPowerEditText);
                            GeyserRating = view.findViewById(R.id.geyserRatingEditText);

                            String roomNoGeyser = GeyserRoomNo.getText().toString();
                            String brandGeyser = GeyserCompanyName.getText().toString();
                            String modelGeyser = GeyserModel.getText().toString();
                            String daysGeyser = GeyserDays.getText().toString();
                            String capacityGeyser = GeyserCapacity.getText().toString();
                            String powerGeyser = GeyserPower.getText().toString();
                            String ratingGeyser = GeyserRating.getText().toString();
                            String uidGeyser = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoGeyser.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoGeyser + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (roomNoGeyser.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Room Number Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    if (roomNoGeyser != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidGeyser, "Geyser", brandGeyser, capacityGeyser);
                                        databaseReference.child("Rooms").child(roomNoGeyser).child("Appliance").child(uidGeyser).setValue(roomApplianceDetails);
                                    }
                                    GeyserDetails geyserDetails = new GeyserDetails(uidGeyser, roomNoGeyser, brandGeyser, modelGeyser, daysGeyser, capacityGeyser, powerGeyser, ratingGeyser);
                                    databaseReference.child("Appliances").child("Geyser").child(uidGeyser).setValue(geyserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }

                            break;
                        case "Washing Machine":

                            WashingMachineRoomNo = view.findViewById(R.id.wmRoomNumberEditText);
                            WashingMachineCompanyName = view.findViewById(R.id.wmCompanyNameEditText);
                            WashingMachineModel = view.findViewById(R.id.wmModelEditText);
                            WashingMachineCapacity = view.findViewById(R.id.wmCapacityEditText);
                            WashingMachinePower = view.findViewById(R.id.wmPowerEditText);
                            WashingMachineRating = view.findViewById(R.id.wmRatingEditText);
                            WashingMachineType = view.findViewById(R.id.wmTypeEditText);

                            String roomNoWashingMachine = WashingMachineRoomNo.getText().toString();
                            String brandWashingMachine = WashingMachineCompanyName.getText().toString();
                            String modelWashingMachine = WashingMachineModel.getText().toString();
                            String daysWashingMachine = WashingMachineDays.getText().toString();
                            String capacityWashingMachine = WashingMachineCapacity.getText().toString();
                            String powerWashingMachine = WashingMachinePower.getText().toString();
                            String ratingWashingMachine = WashingMachineRating.getText().toString();
                            String typeWashingMachine = WashingMachineType.getText().toString();
                            String uidWashingMachine = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoWashingMachine.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoWashingMachine + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandWashingMachine.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoWashingMachine != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidWashingMachine, "Washing Machine", capacityWashingMachine, ratingWashingMachine);
                                        databaseReference.child("Rooms").child(roomNoWashingMachine).child("Appliance").child(uidWashingMachine).setValue(roomApplianceDetails);
                                    }

                                    WashingMachineDetails washingMachineDetails = new WashingMachineDetails(uidWashingMachine, roomNoWashingMachine, brandWashingMachine, modelWashingMachine, daysWashingMachine, capacityWashingMachine, powerWashingMachine, ratingWashingMachine, typeWashingMachine);
                                    databaseReference.child("Appliances").child("Washing Machine").child(uidWashingMachine).setValue(washingMachineDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }

                            break;
                        case "RO":

                            ROCapacity = view.findViewById(R.id.ROCapacityEditText);
                            ROCompanyName = view.findViewById(R.id.ROCompanyNameEditText);
                            ROModel = view.findViewById(R.id.ROModelEditText);
                            RORoomNo = view.findViewById(R.id.RORoomNumberEditText);

                            String capacityRO = ROCapacity.getText().toString();
                            String brandRO = ROCompanyName.getText().toString();
                            String daysRO = RODays.getText().toString();
                            String modelRO = ROModel.getText().toString();
                            String roomNoRO = RORoomNo.getText().toString();
                            String uidRO = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoRO.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoRO + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandRO.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoRO != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidRO, "RO", brandRO, capacityRO);
                                        databaseReference.child("Rooms").child(roomNoRO).child("Appliance").child(uidRO).setValue(roomApplianceDetails);
                                    }
                                    RODetails roDetails = new RODetails(uidRO, roomNoRO, brandRO, modelRO, daysRO, capacityRO);
                                    databaseReference.child("Appliances").child("RO").child(uidRO).setValue(roDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }

                            break;
                        case "Dishwasher":

                            DishwasherCapacity = view.findViewById(R.id.dishwasherCapacityEditText);
                            DishwasherCompanyName = view.findViewById(R.id.dishwasherCompanyNameEditText);
                            DishwasherModel = view.findViewById(R.id.dishwasherModelEditText);
                            DishwasherRoomNo = view.findViewById(R.id.dishwasherRoomNumberEditText);
                            DishwasherType = view.findViewById(R.id.dishwasherTypeEditText);

                            String capacityDishwasher = DishwasherCapacity.getText().toString();
                            String brandDishwasher = DishwasherCompanyName.getText().toString();
                            String daysDishwasher = DishwasherDays.getText().toString();
                            String modelDishwasher = DishwasherModel.getText().toString();
                            String roomNoDishwasher = DishwasherRoomNo.getText().toString();
                            String typeDishwasher = DishwasherType.getText().toString();
                            String uidDishwasher = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoDishwasher.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoDishwasher + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandDishwasher.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoDishwasher != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidDishwasher, "Dishwasher", brandDishwasher, capacityDishwasher);
                                        databaseReference.child("Rooms").child(roomNoDishwasher).child("Appliance").child(uidDishwasher).setValue(roomApplianceDetails);
                                    }
                                    DishwasherDetails dishwasherDetails = new DishwasherDetails(uidDishwasher, roomNoDishwasher, brandDishwasher, modelDishwasher, daysDishwasher, capacityDishwasher, typeDishwasher);
                                    databaseReference.child("Appliances").child("Dishwasher").child(uidDishwasher).setValue(dishwasherDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                            break;
                        case "Microwave":

                            MicrowaveCapacity = view.findViewById(R.id.microwaveCapacityEditText);
                            MicrowaveCompanyName = view.findViewById(R.id.microwaveCompanyNameEditText);
                            MicrowaveModel = view.findViewById(R.id.microwaveModelEditText);
                            MicrowaveType = view.findViewById(R.id.microwaveTypeEditText);
                            MicrowaveRoomNo = view.findViewById(R.id.microwaveRoomNumberEditText);

                            String capacityMicrowave = MicrowaveCapacity.getText().toString();
                            String brandMicrowave = MicrowaveCompanyName.getText().toString();
                            String daysMicrowave = MicrowaveDays.getText().toString();
                            String modelMicrowave = MicrowaveModel.getText().toString();
                            String typeMicrowave = MicrowaveType.getText().toString();
                            String roomNoMicrowave = MicrowaveRoomNo.getText().toString();
                            String uidMicrowave = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoMicrowave.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoMicrowave + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandMicrowave.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoMicrowave != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidMicrowave, "Microwave", brandMicrowave, capacityMicrowave);
                                        databaseReference.child("Rooms").child(roomNoMicrowave).child("Appliance").child(uidMicrowave).setValue(roomApplianceDetails);
                                    }

                                    MicrowaveDetails microwaveDetails = new MicrowaveDetails(uidMicrowave, roomNoMicrowave, brandMicrowave, modelMicrowave, daysMicrowave, capacityMicrowave, typeMicrowave);
                                    databaseReference.child("Appliances").child("Microwave").child(uidMicrowave).setValue(microwaveDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }

                            }

                            break;
                        case "Refrigerator":

                            FridgeCapacity = view.findViewById(R.id.fridgeCapacityEditText);
                            FridgeCompanyName = view.findViewById(R.id.fridgeCompanyNameEditText);
                            FridgeModel = view.findViewById(R.id.fridgeModelEditText);
                            FridgeRoomNo = view.findViewById(R.id.fridgeRoomNumberEditText);
                            FridgeType = view.findViewById(R.id.fridgeTypeEditText);
                            FridgeRating = view.findViewById(R.id.fridgeRatingEditText);

                            String capacityFridge = FridgeCapacity.getText().toString();
                            String brandFridge = FridgeCompanyName.getText().toString();
                            String daysFridge = FridgeDays.getText().toString();
                            String modelFridge = FridgeModel.getText().toString();
                            String roomNoFridge = FridgeRoomNo.getText().toString();
                            String typeFridge = FridgeType.getText().toString();
                            String ratingFridge = FridgeRating.getText().toString();
                            String uidFridge = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoFridge.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoFridge + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandFridge.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    if (roomNoFridge != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidFridge, "Refrigerator", brandFridge, capacityFridge);
                                        databaseReference.child("Rooms").child(roomNoFridge).child("Appliance").child(uidFridge).setValue(roomApplianceDetails);
                                    }

                                    RefrigeratorDetails refrigeratorDetails = new RefrigeratorDetails(uidFridge, roomNoFridge, brandFridge, modelFridge, daysFridge, capacityFridge, typeFridge, ratingFridge);
                                    databaseReference.child("Appliances").child("Refrigerator").child(uidFridge).setValue(refrigeratorDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            break;
                        case "TV":

                            TVCompanyName = view.findViewById(R.id.TVCompanyNameEditText);
                            TVModel = view.findViewById(R.id.TVModelEditText);
                            TVResolution = view.findViewById(R.id.TVResolutionEditText);
                            TVRoomNo = view.findViewById(R.id.TVRoomNumberEditText);
                            TVSize = view.findViewById(R.id.TVSizeEditText);
                            TVType = view.findViewById(R.id.TVTypeEditText);

                            String brandTV = TVCompanyName.getText().toString();
                            String daysTV = TVDays.getText().toString();
                            String modelTV = TVModel.getText().toString();
                            String resolutionTV = TVResolution.getText().toString();
                            String roomNoTV = TVRoomNo.getText().toString();
                            String sizeTV = TVSize.getText().toString();
                            String typeTV = TVType.getText().toString();
                            String uidTV = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoTV.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoTV + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandTV.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {
                                    if (roomNoTV != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidTV, "TV", sizeTV, typeTV);
                                        databaseReference.child("Rooms").child(roomNoTV).child("Appliance").child(uidTV).setValue(roomApplianceDetails);
                                    }
                                    TVDetails tvDetails = new TVDetails(uidTV, roomNoTV, brandTV, modelTV, daysTV, typeTV, sizeTV, resolutionTV);
                                    databaseReference.child("Appliances").child("TV").child(uidTV).setValue(tvDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }

                            break;
                        case "CCTV":

                            CCTVChanel = view.findViewById(R.id.CCTVChannelEditText);
                            CCTVCompanyName = view.findViewById(R.id.CCTVCompanyNameEditText);
                            CCTVModel = view.findViewById(R.id.CCTVModelEditText);
                            CCTVNight = view.findViewById(R.id.CCTVNightEditText);
                            CCTVResolution = view.findViewById(R.id.CCTVResolutionEditText);
                            CCTVRoomNo = view.findViewById(R.id.CCTVRoomNumberEditText);

                            String channelCCTV = CCTVChanel.getText().toString();
                            String brandCCTV = CCTVCompanyName.getText().toString();
                            String daysCCTV = CCTVDays.getText().toString();
                            String modelCCTV = CCTVModel.getText().toString();
                            String nightCCTV = CCTVNight.getText().toString();
                            String resolutionCCTV = CCTVResolution.getText().toString();
                            String roomNoCCTV = CCTVRoomNo.getText().toString();
                            String uidCCTV = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoCCTV.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoCCTV + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (roomNoCCTV.equals("") && brandCCTV.equals("") && daysCCTV.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Room number Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoCCTV != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidCCTV, "CCTV", brandCCTV, resolutionCCTV);
                                        databaseReference.child("Rooms").child(roomNoCCTV).child("Appliance").child(uidCCTV).setValue(roomApplianceDetails);
                                    }
                                    CCTVDetails cctvDetails = new CCTVDetails(uidCCTV, roomNoCCTV, brandCCTV, modelCCTV, daysCCTV, nightCCTV, channelCCTV, resolutionCCTV);
                                    databaseReference.child("Appliances").child("CCTV").child(uidCCTV).setValue(cctvDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            break;
                        case "Iron":

                            IronCompanyName = view.findViewById(R.id.ironCompanyNameEditText);
                            IronModel = view.findViewById(R.id.ironModelEditText);
                            IronPower = view.findViewById(R.id.ironPowerEditText);
                            IronRoomNo = view.findViewById(R.id.ironRoomNumberEditText);

                            String brandIron = IronCompanyName.getText().toString();
                            String daysIron = IronDays.getText().toString();
                            String modelIron = IronModel.getText().toString();
                            String powerIron = IronPower.getText().toString();
                            String roomNoIron = IronRoomNo.getText().toString();
                            String uidIron = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoIron.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoIron + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandIron.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    if (roomNoIron != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidIron, "Iron", brandIron, modelIron);
                                        databaseReference.child("Rooms").child(roomNoIron).child("Appliance").child(uidIron).setValue(roomApplianceDetails);
                                    }
                                    IronDetails ironDetails = new IronDetails(uidIron, roomNoIron, brandIron, modelIron, daysIron, powerIron);
                                    databaseReference.child("Appliances").child("Iron").child(uidIron).setValue(ironDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }

                            break;
                        case "Induction":

                            InductionCompanyName = view.findViewById(R.id.inductionCompanyNameEditText);
                            InductionModel = view.findViewById(R.id.inductionModelEditText);
                            InductionNoCooktop = view.findViewById(R.id.inductionNumberCooktopEditText);
                            InductionPower = view.findViewById(R.id.inductionPowerEditText);
                            InductionRoomNo = view.findViewById(R.id.inductionRoomNumberEditText);
                            InductionType = view.findViewById(R.id.inductionControlTypeEditText);

                            String brandInduction = InductionCompanyName.getText().toString();
                            String daysInduction = InductionDays.getText().toString();
                            String modelInduction = InductionModel.getText().toString();
                            String noCooktopInduction = InductionNoCooktop.getText().toString();
                            String powerInduction = InductionPower.getText().toString();
                            String roomNoInduction = InductionRoomNo.getText().toString();
                            String typeInduction = InductionType.getText().toString();
                            String uidInduction = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoInduction.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoInduction + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandInduction.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    if (roomNoInduction != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidInduction, "Induction", brandInduction, powerInduction);
                                        databaseReference.child("Rooms").child(roomNoInduction).child("Appliance").child(uidInduction).setValue(roomApplianceDetails);
                                    }
                                    InductionDetails inductionDetails = new InductionDetails(uidInduction, roomNoInduction, brandInduction, modelInduction, daysInduction, powerInduction, typeInduction, noCooktopInduction);
                                    databaseReference.child("Appliances").child("Induction").child(uidInduction).setValue(inductionDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }

                            break;
                        case "Router":

                            RouterAntenna = view.findViewById(R.id.routerAntennaEditText);
                            RouterCompanyName = view.findViewById(R.id.routerCompanyNameEditText);
                            RouterModel = view.findViewById(R.id.routerModelEditText);
                            RouterRoomNo = view.findViewById(R.id.routerRoomNumberEditText);
                            RouterSpeed = view.findViewById(R.id.routerSpeedEditText);

                            String antennaRouter = RouterAntenna.getText().toString();
                            String brandRouter = RouterCompanyName.getText().toString();
                            String daysRouter = RouterDays.getText().toString();
                            String modelRouter = RouterModel.getText().toString();
                            String roomNoRouter = RouterRoomNo.getText().toString();
                            String speedRouter = RouterSpeed.getText().toString();
                            String uidRouter = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoRouter.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoRouter + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (daysRouter.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Installation Date Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    if (roomNoRouter != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidRouter, "Router", brandRouter, speedRouter);
                                        databaseReference.child("Rooms").child(roomNoRouter).child("Appliance").child(uidRouter).setValue(roomApplianceDetails);
                                    }

                                    RouterDetails routerDetails = new RouterDetails(uidRouter, roomNoRouter, brandRouter, modelRouter, daysRouter, antennaRouter, speedRouter);
                                    databaseReference.child("Appliances").child("Router").child(uidRouter).setValue(routerDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                            break;
                        case "Heater":

                            HeaterCompanyName = view.findViewById(R.id.heaterCompanyNameEditText);
                            HeaterModel = view.findViewById(R.id.heaterModelEditText);
                            HeaterPower = view.findViewById(R.id.heaterPowerEditText);
                            HeaterRoomNo = view.findViewById(R.id.heaterRoomNumberEditText);
                            HeaterWeight = view.findViewById(R.id.heaterWeightEditText);

                            String brandHeater = HeaterCompanyName.getText().toString();
                            String daysHeater = HeaterDays.getText().toString();
                            String modelHeater = HeaterModel.getText().toString();
                            String powerHeater = HeaterPower.getText().toString();
                            String roomNoHeater = HeaterRoomNo.getText().toString();
                            String weightHeater = HeaterWeight.getText().toString();
                            String uidHeater = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoHeater.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoHeater + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (roomNoHeater.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Room number Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();

                                } else {

                                    if (roomNoHeater != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidHeater, "Heater", brandHeater, powerHeater);
                                        databaseReference.child("Rooms").child(roomNoHeater).child("Appliance").child(uidHeater).setValue(roomApplianceDetails);
                                    }
                                    HeaterDetails heaterDetails = new HeaterDetails(uidHeater, roomNoHeater, brandHeater, modelHeater, daysHeater, powerHeater, weightHeater);
                                    databaseReference.child("Appliances").child("Heater").child(uidHeater).setValue(heaterDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }

                            }
                            break;
                        case "D2H":

                            D2HCompanyName = view.findViewById(R.id.D2HCompanyNameEditText);
                            D2HRoomNo = view.findViewById(R.id.D2HRoomNumberEditText);

                            String brandD2H = D2HCompanyName.getText().toString();
                            String daysD2H = D2HDays.getText().toString();
                            String roomNoD2H = D2HRoomNo.getText().toString();
                            String uidD2H = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoD2H.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoD2H + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (brandD2H.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {

                                    if (roomNoD2H != null) {
                                        RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidD2H, "D2H", brandD2H, daysD2H);
                                        databaseReference.child("Rooms").child(roomNoD2H).child("Appliance").child(uidD2H).setValue(roomApplianceDetails);
                                    }

                                    D2HDetails d2HDetails = new D2HDetails(uidD2H, roomNoD2H, brandD2H, daysD2H);
                                    databaseReference.child("Appliances").child("D2H").child(uidD2H).setValue(d2HDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                            break;
                        case "Other":

                            OtherCompanyName = view.findViewById(R.id.otherCompanyNameEditText);
                            OtherName = view.findViewById(R.id.otherNameEditText);
                            OtherRoomNo = view.findViewById(R.id.otherRoomNumberEditText);

                            String brandOther = OtherCompanyName.getText().toString();
                            String nameOther = OtherName.getText().toString();
                            String roomNoOther = OtherRoomNo.getText().toString();
                            String uidOther = databaseReference.push().getKey();

                            for (String thisRoom : rooms) {

                                if (roomNoOther.equalsIgnoreCase(thisRoom)) {
                                    flag = true;
                                }
                            }

                            if (!flag) {

                                Toast.makeText(ApplianceDetailsActivity.this, "Room number " + roomNoOther + " is not available.", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();

                            }
                            else {

                                if (roomNoOther != null) {
                                    RoomApplianceDetails roomApplianceDetails = new RoomApplianceDetails(uidOther, "AC", nameOther, brandOther);
                                    databaseReference.child("Rooms").child(roomNoOther).child("Appliance").child(uidOther).setValue(roomApplianceDetails);
                                }
                                if (brandOther.equals("") && nameOther.equals("")) {

                                    Toast.makeText(ApplianceDetailsActivity.this, "Brand and Name Required", Toast.LENGTH_LONG).show();
                                    progressDialog.dismiss();
                                } else {
                                    OtherApplianceDetails otherApplianceDetails = new OtherApplianceDetails(uidOther, roomNoOther, nameOther, brandOther);
                                    databaseReference.child("Appliances").child("Other").child(uidOther).setValue(otherApplianceDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Saved!", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            progressDialog.dismiss();
                                            Toast.makeText(ApplianceDetailsActivity.this, "Failed!", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                            break;
                    }

                }else{
                    Toast.makeText(ApplianceDetailsActivity.this, "Check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        builder.setNegativeButton("Cancel", null);

        builder.show();

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ApplianceDetailsActivity.this , ApplianceActivity.class ));
        finish();
    }
}
