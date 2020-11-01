package com.vironit.onlinepharmacy.dao.jpa;

import com.vironit.onlinepharmacy.dao.RecipeDao;
import com.vironit.onlinepharmacy.model.Recipe;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Collection;
import java.util.Optional;

@Repository
public class JpaRecipeDao implements RecipeDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    @Override
    public long add(Recipe recipe) {
        entityManager.persist(recipe);
        return recipe.getId();
    }

    @Override
    public Optional<Recipe> get(long id) {
        Recipe recipe = entityManager.find(Recipe.class, id);
        entityManager.detach(recipe);
        return Optional.ofNullable(recipe);
    }

    @Override
    public Collection<Recipe> getAll() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> root = criteriaQuery.from(Recipe.class);
        criteriaQuery.select(root);
        return entityManager.createQuery(criteriaQuery)
                .getResultList();
    }

    @Transactional
    @Override
    public boolean remove(long id) {
        Recipe recipe = entityManager.find(Recipe.class, id);
        entityManager.remove(recipe);
        return true;
    }

    @Override
    public Optional<Recipe> getByOrderPositionId(long id) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Recipe> criteriaQuery = criteriaBuilder.createQuery(Recipe.class);
        Root<Recipe> root = criteriaQuery.from(Recipe.class);
        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(root.get("orderPosition")
                        .get("id"), id));
        try {
            return Optional.of(entityManager.createQuery(criteriaQuery)
                    .getSingleResult());
        } catch (NoResultException noResultException) {
            return Optional.empty();
        }
    }
}
