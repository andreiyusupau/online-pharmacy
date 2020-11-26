package com.vironit.onlinepharmacy.dao;

import com.vironit.onlinepharmacy.model.CreditCard;
/**
 * @deprecated Replaced with {@link com.vironit.onlinepharmacy.repository.CreditCardRepository}
 */
@Deprecated
public interface CreditCardDao extends ImmutableDao<CreditCard>, SlaveDao<CreditCard> {

}
