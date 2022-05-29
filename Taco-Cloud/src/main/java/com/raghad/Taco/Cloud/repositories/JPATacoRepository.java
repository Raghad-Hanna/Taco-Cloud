package com.raghad.Taco.Cloud.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.raghad.Taco.Cloud.models.Taco;

public interface JPATacoRepository extends PagingAndSortingRepository<Taco, Long> {
}
