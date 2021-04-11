package walter.study.restaurant.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import walter.study.restaurant.domain.Region;
import walter.study.restaurant.domain.RegionRepository;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class RegionService {

    private final RegionRepository regionRepository;

    public List<Region> getRegions() {

        return regionRepository.findAll();
    }

    public Region addRegion(String name) {

        Region region = Region.builder().name(name).build();

        regionRepository.save(region);

        return region;
    }
}
