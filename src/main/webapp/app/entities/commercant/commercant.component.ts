import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ICommercant } from 'app/shared/model/commercant.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { CommercantService } from './commercant.service';
import { CommercantDeleteDialogComponent } from './commercant-delete-dialog.component';

@Component({
  selector: 'jhi-commercant',
  templateUrl: './commercant.component.html',
})
export class CommercantComponent implements OnInit, OnDestroy {
  commercants: ICommercant[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected commercantService: CommercantService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.commercants = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.commercantService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<ICommercant[]>) => this.paginateCommercants(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.commercants = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInCommercants();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ICommercant): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInCommercants(): void {
    this.eventSubscriber = this.eventManager.subscribe('commercantListModification', () => this.reset());
  }

  delete(commercant: ICommercant): void {
    const modalRef = this.modalService.open(CommercantDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.commercant = commercant;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateCommercants(data: ICommercant[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.commercants.push(data[i]);
      }
    }
  }
}
