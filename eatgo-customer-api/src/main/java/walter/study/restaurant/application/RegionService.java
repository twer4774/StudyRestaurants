package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.Region;
import walter.study.restaurant.domain.RegionRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public List<Region> getRegions() {

        return regionRepository.findAll();
    }

}
