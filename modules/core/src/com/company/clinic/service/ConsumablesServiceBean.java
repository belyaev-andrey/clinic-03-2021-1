package com.company.clinic.service;

import com.company.clinic.entity.Consumable;
import com.haulmont.cuba.core.EntityManager;
import com.haulmont.cuba.core.Persistence;
import com.haulmont.cuba.core.Transaction;
import com.haulmont.cuba.core.TypedQuery;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service(ConsumablesService.NAME)
public class ConsumablesServiceBean implements ConsumablesService {

    @Inject
    private Persistence persistence;

    @Override
    @Transactional
    public List<Consumable> getUsedConsumables() {

        EntityManager em = persistence.getEntityManager();

        TypedQuery<Consumable> query = em.createQuery(
                "select distinct c from clinic_Visit v join v.consumables c " +
                        "where @between(c.createTs, now-7, now+1, day)", Consumable.class);

        query.setViewName(View.LOCAL);

        return query.getResultList();

    }

}