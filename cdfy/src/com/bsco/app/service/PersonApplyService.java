package com.bsco.app.service;

import org.springframework.dao.PessimisticLockingFailureException;

import com.bsco.app.model.PersonApply;
import com.bsco.framework.service.EntityService;

public interface PersonApplyService extends EntityService<PersonApply>{

}
