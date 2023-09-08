<?php

namespace App\Entity;

use App\Repository\StationRepository;
use Doctrine\Common\Collections\ArrayCollection;
use Doctrine\Common\Collections\Collection;
use Doctrine\ORM\Mapping as ORM;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Serializer\Annotation\Groups;
use App\Entity\Ligne; 

#[ORM\Entity(repositoryClass: StationRepository::class)]
class Station
{
    #[ORM\Id]
    #[ORM\GeneratedValue]
    #[ORM\Column(type: 'integer')]
    private ?int $id = null;

    #[ORM\Column(length: 20, nullable: true)]
    #[Groups("station")]
    private ?string $long_alt = null;

    #[ORM\ManyToOne(targetEntity: Ligne::class)]
    #[ORM\JoinColumn(nullable: false)]
    private ?Ligne $ligne = null;

    

    

    public function getId(): ?int
    {
        return $this->id;
    }

    public function getLongAlt(): ?string
    {
        return $this->long_alt;
    }

    public function setLongAlt(string $long_alt): self
    {
        $this->long_alt = $long_alt;

        return $this;
    }
    
    public function getLigne(): ?Ligne
    {
        return $this->ligne;
    }

    public function setLigne(?Ligne $ligne): self
    {
        $this->ligne = $ligne;

        return $this;
    }

}
