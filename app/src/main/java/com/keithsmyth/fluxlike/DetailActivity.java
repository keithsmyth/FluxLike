package com.keithsmyth.fluxlike;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.keithsmyth.fluxlike.arch.flux.Dispatcher;
import com.keithsmyth.fluxlike.arch.recycler.Adapter;
import com.keithsmyth.fluxlike.arch.recycler.FeatureControllerGroup;
import com.keithsmyth.fluxlike.features.header.HeaderController;
import com.keithsmyth.fluxlike.features.options.OptionsController;

import java.util.Arrays;

public class DetailActivity extends AppCompatActivity implements LifecycleRegistryOwner {

    private final LifecycleRegistry registry = new LifecycleRegistry(this);

    private Dispatcher dispatcher;

    @Override
    public LifecycleRegistry getLifecycle() {
        return registry;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Adapter adapter = new Adapter();

        RecyclerView recycler = (RecyclerView) findViewById(R.id.recycler);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        dispatcher = new Dispatcher();
        ViewModelProvider viewModelProvider = ViewModelProviders.of(this);

        // subscribe adapter features to store
        new FeatureControllerGroup(adapter, this,
            Arrays.asList(
                new HeaderController(viewModelProvider, dispatcher),
                new OptionsController(viewModelProvider, dispatcher)
            ));

        // init data
        viewModelProvider.get(DetailActivityViewModel.class).init(dispatcher);
    }

    @Override
    protected void onDestroy() {
        dispatcher.clear();
        super.onDestroy();
    }
}
